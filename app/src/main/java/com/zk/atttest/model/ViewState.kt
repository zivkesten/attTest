package com.zk.atttest.model

data class DetailViewState(
    val backDrop: String? = null,
    val title: String? = null,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)

data class ListViewState(
    val adapterList: List<Item> = emptyList()
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