package com.fetchcodeexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * displays top bar that gives information about the current table being displayed as well as
 * the dropdown menu for selecting the listID one wants to look at
 * @param title top bar title to display
 * @param viewModel main view model, used by child dropdown component
 * @param options list of possible listIDs to select on, also used by child dropdown component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, viewModel: MainViewModel, options: List<Int>) {
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
            Dropdown(viewModel, options)
        },
    )
}

/**
 * displays the dropdown menu and button for the list ID selection
 * @param viewModel main view model, used to update the list ID field
 * @param options all the possible individual list IDs
 */
@Composable
private fun Dropdown(viewModel: MainViewModel, options: List<Int>) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "select list ID to display"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // display the descriptor and the all-IDs option
            DropdownMenuItem(enabled = false, text = { Text("Select List ID:") }, onClick = {})
            DropdownMenuItem(
                text = { Text("All") },
                onClick = { viewModel.selectId(-1) }
            )
            // display the individual ID options
            for (listId in options) {
                DropdownMenuItem(
                    text = { Text("$listId") },
                    onClick = { viewModel.selectId(listId) }
                )
            }
        }
    }
}