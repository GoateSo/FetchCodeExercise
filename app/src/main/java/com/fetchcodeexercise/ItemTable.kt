package com.fetchcodeexercise

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fetchcodeexercise.utils.ItemObject

/**
 * helper for creating an individual cell of the Item table display
 * @param text the display string , either the name or the ID
 * @param weight percentage of width it takes up, should add up to 1.0
 * @param color color for the inside text
 */
@Composable
private fun RowScope.Cell(
    text: String,
    weight: Float,
    color: Color = Color.Black
) {
    Text(
        text = text,
        color = color,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .padding(8.dp)
            .weight(weight)
    )
}

/**
 * displays the three column table that contains all the item entries
 * @param listItems items to display
 * @param scrollState state for the column scrolling, used to reset when the ListID is changed
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemTable(listItems: List<ItemObject>, scrollState: LazyListState) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = scrollState) {
        stickyHeader {
            Row(Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
                Cell("Item ID", .25f, MaterialTheme.colorScheme.secondary)
                Cell("List ID", .25f, MaterialTheme.colorScheme.secondary)
                Cell("Name", .5f, MaterialTheme.colorScheme.secondary)
            }
        }
        items(listItems.size) { i ->
            Row(Modifier.fillMaxWidth()) {
                Cell("${listItems[i].id}", .25f)
                Cell("${listItems[i].listID}", .25f)
                Cell(listItems[i].name, .5f)
            }
        }
    }
}