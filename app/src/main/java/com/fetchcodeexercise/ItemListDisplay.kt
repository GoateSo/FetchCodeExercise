package com.fetchcodeexercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fetchcodeexercise.utils.ItemObject

/**
 * Sort the list of items to display
 * @param unsortedItems list of items to sort
 */
private fun getSortedItems(
    unsortedItems: List<ItemObject>
): List<ItemObject> {
    // prioritize sorting with listID, and then compare name/ID based on specified regime
    return unsortedItems.sortedWith { item1, item2 ->
        when {
            item1.listid != item2.listid -> item1.listid.compareTo(item2.listid)
            else -> item1.name.compareTo(item2.name)
        }
    }
}

/**
 * sorts and displays the item table, along with some padding at the top
 * @param unsortedItems items from the json, as they appear after grouping
 */
@Composable
fun ItemListDisplay(
    unsortedItems: List<ItemObject>,
    modifier: Modifier
) {
    Box(modifier) {
        ItemTable(getSortedItems(unsortedItems))
    }
}