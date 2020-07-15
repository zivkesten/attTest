package com.zk.atttest.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.zk.atttest.model.Event
import com.zk.atttest.model.Item
import com.zk.atttest.model.ListViewState
import com.zk.atttest.model.ViewEffect
import com.zk.atttest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository) : ViewModel() {

	private val viewState = MutableLiveData<ListViewState>()

	private val viewAction = MutableLiveData<ViewEffect>()

	val obtainState: LiveData<ListViewState> = viewState

	val obtainViewEffects: LiveData<ViewEffect> = viewAction

	private var currentViewState = ListViewState()
		set(value) {
			field = value
			viewState.value = value
		}

	fun event(event: Event) {
		when(event) {
			is Event.ScreenLoad, Event.SwipeToRefreshEvent -> getUsersFromApi(10)
			is Event.ListItemClicked -> viewAction.postValue(ViewEffect.TransitionToScreen(event.item))
		}
	}

	private fun getUsersFromApi(numOfUsers: Int) {
		viewModelScope.launch {
			val mutableList = ArrayList<Item>()
			repeat(numOfUsers) {
				val usersResult = loadUsersFromApi()
				usersResult?.results?.first()?.let { it1 -> mutableList.add(it1) }
			}
			currentViewState = currentViewState.copy(adapterList = mutableList)
		}
	}

	private suspend fun loadUsersFromApi() =
		withContext(Dispatchers.IO) {
			repository.getUsers()
		}
}