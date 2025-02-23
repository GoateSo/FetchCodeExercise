package com.fetchcodeexercise

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
 * @param viewModel main view model, used to get json grouping and sort type in child component
 */
@Composable
fun ItemListApplication(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val status = uiState.status
    val dataMap = uiState.jsonData
    val listId = uiState.curListId
    val showAllLists = listId == -1
    // display main app body, also display alt title if unable to request data (e.g. no wifi)
    val topBarTitle = when {
        status == RequestStatus.FAILURE -> "Data Request Failure"
        showAllLists -> "Showing Items from all Lists"
        else -> "Showing Items with ListID $listId"
    }
    Scaffold(topBar = {
        TopBar(topBarTitle, viewModel, dataMap.keys.toList())
    }) { innerPadding ->
        when (status) {
            RequestStatus.SUCCESS -> { // display received data
                val dataItems =
                    if (showAllLists) dataMap.values.flatten() else dataMap.getValue(listId)
                ItemListDisplay(dataItems, Modifier.padding(innerPadding))
            }

            RequestStatus.FAILURE -> { // alt text for failed request
                AltText(
                    "Unable to fetch necessary data, check your internet connection",
                    innerPadding
                )
            }

            RequestStatus.LOADING -> { // alt text for when loading period
                AltText("Loading data from network...", innerPadding)
            }
        }
    }
}

@Composable
private fun AltText(text: String, innerPadding: PaddingValues) {
    Text(
        text = text,
        modifier = Modifier.padding(
            vertical = innerPadding.calculateTopPadding().plus(10.dp),
            horizontal = 10.dp
        ),
    )
}