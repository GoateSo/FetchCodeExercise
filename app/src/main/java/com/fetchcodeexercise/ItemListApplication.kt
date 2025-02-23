package com.fetchcodeexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fetchcodeexercise.utils.RequestStatus

/**
 * top level application UI that combines the item table display with the top bar
 * @param viewModel main view model, used to get request status and json objects
 */
@Composable
fun ItemListApplication(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mainScrollState = rememberLazyListState()
    // display main app body, also display alt. contents on load or on failure (e.g. no wifi)
    Scaffold(topBar = {
        TopBar(viewModel, uiState, mainScrollState)
    }) { innerPadding ->
        val basePadding = Modifier.padding(innerPadding)
        when (uiState.status) {
            RequestStatus.SUCCESS -> { // display received data from the selected list
                // for the case of displaying all items: flatten preserves the name-sorted structure
                // and jsonData being a sorted map means it should also be sorted in order of listID
                val dataMap = uiState.jsonData
                val items = when (uiState.curListId) {
                    -1 -> dataMap.values.flatten()
                    else -> dataMap.getValue(uiState.curListId)
                }
                Box(modifier = basePadding) {
                    ItemTable(items, mainScrollState)
                }
            }

            RequestStatus.FAILURE -> { // alt text for failed request, also allow user to retry
                Text(
                    "Unable to fetch necessary data, check your internet connection and retry.",
                    basePadding.padding(10.dp)
                )
            }

            RequestStatus.LOADING -> { // alt text for when loading period
                Text("Loading data from network...", basePadding.padding(10.dp))
            }
        }
    }
}