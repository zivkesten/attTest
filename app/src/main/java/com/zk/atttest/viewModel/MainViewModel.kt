package com.zk.atttest.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.zk.atttest.model.*
import com.zk.atttest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

	private val NUMBER_OF_ITEMS_IN_PAGE = 10

	private val viewState = MutableLiveData<ListViewState>()

	private val viewAction = MutableLiveData<ViewEffect>()

	val obtainState: LiveData<ListViewState> = viewState

	val obtainViewEffects: LiveData<ViewEffect> = viewAction

	private var currentViewState = ListViewState()
		set(value) {
			field = value
			viewState.postValue(value)
		}

	fun event(event: Event) {
		when(event) {
			is Event.ScreenLoad, Event.SwipeToRefreshEvent -> getUsersFromApi(NUMBER_OF_ITEMS_IN_PAGE)
			is Event.ListItemClicked -> viewAction.postValue(ViewEffect.TransitionToScreen(event.item))
		}
	}

	private fun getUsersFromApi(numOfUsers: Int) {
		try {
			viewModelScope.launch(Dispatchers.IO) {
				val usersFromApi = mutableListOf<UsersResponse?>()
				repeat(numOfUsers) {
					//Wait and execute together async
					val usersFromApiDeferred = async { repository.getUsers() }
					val userFromApi = usersFromApiDeferred.await()
					usersFromApi.add(userFromApi)
				}
				val mutableList = usersFromApi.map {
					(it ?: UsersResponse(errorMessage = "Error response"))
						.results
						.first()
				}
				currentViewState = currentViewState.copy(adapterList = mutableList)
			}
		} catch (e: Exception) {
			Log.e(MainViewModel::class.java.simpleName, "Error loading users ${e.localizedMessage}")
		}
	}
}