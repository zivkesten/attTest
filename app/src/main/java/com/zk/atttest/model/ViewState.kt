package com.zk.atttest.model

import android.view.View

data class DetailViewState(
    val backDrop: String? = null,
    val title: String? = null,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)

data class ListViewState(
    val adapterList: List<Item> = emptyList(),
    val loadingStateVisibility: Int? = View.GONE,
    val errorMessage: String? = null,
    val errorMessageVisibility: Int? = View.GONE
)

sealed class ViewEffect {
    data class TransitionToScreen(val item: Item) : ViewEffect()
}

sealed class Event {
    object SwipeToRefreshEvent: Event()
    object ScreenLoad: Event()
    data class DataReceived(val data: Item?) : Event()
    data class ListItemClicked(val item: Item): Event()
}

sealed class Result {
    object ScreenLoadResult : Result()
    data class UsersResult(val users: List<Item>) : Result()
}
