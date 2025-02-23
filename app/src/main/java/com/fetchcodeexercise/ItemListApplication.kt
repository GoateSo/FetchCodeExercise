package com.fetchcodeexercise

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
 * @param viewModel main view model, used to get request status and json objects
 */
@Composable
fun ItemListApplication(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // display main app body, also display alt. contents on load or on failure (e.g. no wifi)
    Scaffold(topBar = {
        TopBar(viewModel, uiState)
    }) { innerPadding ->
        when (uiState.status) {
            RequestStatus.SUCCESS -> { // display received data
                ItemListDisplay(uiState, Modifier.padding(innerPadding))
            }

            RequestStatus.FAILURE -> { // alt text for failed request, also allow user to retry
                AltText(
                    "Unable to fetch necessary data, check your internet connection and retry.",
                    Modifier.padding(innerPadding)
                )
            }

            RequestStatus.LOADING -> { // alt text for when loading period
                AltText("Loading data from network...", Modifier.padding(innerPadding))
            }
        }
    }
}

/** template for alt texts displayed on failure and on load */
@Composable
private fun AltText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(10.dp)
    )
}