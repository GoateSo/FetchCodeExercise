package com.fetchcodeexercise.utils

/**
 * container for the core application state
 * @property jsonData list of items parsed from the request
 * @property status request status (success or failure)
 * @property curListId listID in focus, or -1 if viewing all is desired
 */
data class ApplicationState(
    val jsonData: Map<Int, List<ItemObject>> = mapOf(),
    val status: RequestStatus = RequestStatus.LOADING,
    val curListId: Int = -1
)
