package com.fetchcodeexercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.fetchcodeexercise.utils.ApplicationState
import com.fetchcodeexercise.utils.ItemObject
import com.fetchcodeexercise.utils.RequestStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * main view model class for the app, stores the state and the Volley request queue, as well as
 * methods for updating the sorting regime and specifying the list ID to look at
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val reqQueue = Volley.newRequestQueue(app)
    private val _uiState = MutableStateFlow(ApplicationState())
    val uiState = _uiState.asStateFlow()

    // start off by requesting the data
    init {
        requestData()
    }

    /** update the list ID in focus */
    fun selectId(newId: Int) {
        _uiState.update { it.copy(curListId = newId) }
    }

    // does a one-time request request for the necessary data, and stores it in application state
    private fun requestData() {
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
        val jsonQuery = JsonArrayRequest(
            Request.Method.GET, url, null,
            { resp ->
                // convert the JSONArray into a list of structured objects
                // and filter on the given criteria (non-empty, non-null name)
                val filteredItems = (0..<resp.length()).map { resp.getJSONObject(it) }
                    .filter { item ->
                        item.get("name") is String && item.getString("name").isNotEmpty()
                    }.map { item ->
                        ItemObject(
                            item.getInt("id"),
                            item.getInt("listId"),
                            item.getString("name")
                        )
                    }
                // group by listID with sorted set to order by listID, defer sort until display
                val itemGroups = filteredItems.groupByTo(sortedMapOf()) { it.listid }
                // use the parsed and sorted items as the new state
                _uiState.update { it.copy(jsonData = itemGroups, status = RequestStatus.SUCCESS) }
            },
            { _ -> _uiState.update { it.copy(status = RequestStatus.FAILURE) } }
        )
        reqQueue.add(jsonQuery)
    }
}