package com.fetchcodeexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.fetchcodeexercise.utils.ApplicationState
import com.fetchcodeexercise.utils.RequestStatus
import kotlinx.coroutines.launch

/**
 * displays top bar that gives information about the current table being displayed as well as
 * the dropdown menu for selecting the listID one wants to look at
 * @param viewModel main view model, used by child dropdown component
 * @param state UI and application state, used for the status, list IDs, and child components
 * @param scrollState state of scrolling for main item list, reset to index 0 by listID selection
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: MainViewModel, state: ApplicationState, scrollState: LazyListState) {
    val title = when {
        state.status == RequestStatus.LOADING -> "Data Request Loading..."
        state.status == RequestStatus.FAILURE -> "Data Request Failure"
        state.curListId == -1 -> "Showing Items from all Lists"
        else -> "Showing Items with ListID ${state.curListId}"
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            // allow reload if not currently loading
            if (state.status != RequestStatus.LOADING) {
                Reload(viewModel)
            }
            // allow selection when request is successful and there's data
            if (state.status == RequestStatus.SUCCESS) {
                Dropdown(viewModel, state, scrollState)
            }
        },
    )
}

/**
 * displays the dropdown menu and button for the list ID selection
 * @param viewModel main view model, used to update the list ID field
 * @param state application state, used to get the status field and the json data
 */
@Composable
private fun Dropdown(
    viewModel: MainViewModel,
    state: ApplicationState,
    scrollState: LazyListState
) {
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "select list ID to display"
            )
        }
        // if the request was successful
        if (state.status == RequestStatus.SUCCESS) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // display the descriptor and the all-IDs option
                DropdownMenuItem(enabled = false, text = { Text("Select List ID:") }, onClick = {})
                DropdownMenuItem(
                    text = { Text("All") },
                    onClick = {
                        viewModel.selectId(-1)
                        coroutineScope.launch {
                            scrollState.scrollToItem(0)
                        }
                    }
                )
                // display the individual ID options
                for (listId in state.jsonData.keys) {
                    DropdownMenuItem(
                        text = { Text("$listId") },
                        onClick = {
                            viewModel.selectId(listId)
                            coroutineScope.launch {
                                scrollState.scrollToItem(0)
                            }
                        }
                    )
                }
            }
        }
    }
}

/** reload button to refresh data from network, or retry in case of failure */
@Composable
private fun Reload(viewModel: MainViewModel) {
    IconButton(onClick = viewModel::reload) {
        Icon(
            imageVector = Icons.Rounded.Refresh,
            contentDescription = "Reload data from network"
        )
    }
}