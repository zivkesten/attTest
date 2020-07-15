package com.zk.atttest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zk.atttest.model.*

class UserViewModel : ViewModel() {

    private var data: Item? = null

    private val viewState = MutableLiveData<DetailViewState>()

    private val viewAction = MutableLiveData<ViewEffect>()

    val obtainStateDetail: LiveData<DetailViewState> = viewState

    val obtainAction: LiveData<ViewEffect> = viewAction

    private var currentViewState = DetailViewState()
        set(value) {
            viewState.value = value
            field = value
        }

    private fun handleScreenLoadState(item: Item?) {
        item?.let {

            //Make sure to create a new state, do not mutate old state
            currentViewState =
                currentViewState.copy(
                    backDrop = item.picture?.large,
                    title = item.name(),
                    email = item.email,
                    phone = item.phone,
                    address = item.address()
                )
            data = item
        }
    }

    fun event(event: Event) {
        when(event) {
            is Event.DataReceived -> handleScreenLoadState(event.data)
        }
    }
}