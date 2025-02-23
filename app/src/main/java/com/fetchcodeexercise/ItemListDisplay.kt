package com.fetchcodeexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fetchcodeexercise.utils.ApplicationState

/**
 * sorts and displays the item table along with some padding at the top
 * @param state state of the application, used for getting the json data
 * @param modifier base modifier, used to pass on padding information
 */
@Composable
fun ItemListDisplay(
    state: ApplicationState,
    modifier: Modifier
) {
    // for the case of displaying all items: flatten preserves the name-sorted substructure, and jsonData
    // being a sorted map means it should also be sorted in order of listID
    val dataMap = state.jsonData
    val items = when (state.curListId) {
        -1 -> dataMap.values.flatten()
        else -> dataMap.getValue(state.curListId)
    }
    Box(modifier = modifier) {
        ItemTable(items)
    }
}