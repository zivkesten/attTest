package com.zk.atttest.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.zk.atttest.model.*
import com.zk.atttest.repository.Lce
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
		eventToResult(event)
	}

	private fun eventToResult(event: Event) {
		when(event) {
			is Event.ScreenLoad, Event.SwipeToRefreshEvent -> getUsersFromApi(NUMBER_OF_ITEMS_IN_PAGE)
			is Event.ListItemClicked -> viewAction.postValue(ViewEffect.TransitionToScreen(event.item))
		}
	}

	private fun getUsersFromApi(numOfUsers: Int) {
		resultToViewState(Lce.Loading())
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
				val result: Lce<Result> = if (mutableList.isNotEmpty()) {
					Lce.Content(Result.UsersResult(mutableList))
				} else {
					createErrorResponse(java.lang.Exception("Error loading users"))
				}
				resultToViewState(result)
			}
		} catch (e: Exception) {
			Log.e(MainViewModel::class.java.simpleName, "Error loading users ${e.localizedMessage}")
			resultToViewState(createErrorResponse(e))
		}
	}

	private fun createErrorResponse(e: Exception): Lce.Error<Result> {
		val errorItem = Item()
		errorItem.errorMessage = "Error loading users ${e.localizedMessage}"
		return Lce.Error(Result.UsersResult(listOf(errorItem)))
	}

	private fun resultToViewState(result: Lce<Result>) {
		Log.d(MainViewModel::class.java.simpleName, "----- result $result")
		currentViewState = when (result) {
			is Lce.Loading -> {
				currentViewState.copy(
					errorMessageVisibility = View.GONE,
					loadingStateVisibility = View.VISIBLE)
			}
			is Lce.Error -> {
				when (result.packet) {
					is Result.UsersResult -> currentViewState.copy(errorMessage = result.packet.users.first().errorMessage)
					else -> currentViewState.copy(
						errorMessage = "Unexpected error",
						errorMessageVisibility = View.VISIBLE,
						loadingStateVisibility = View.GONE)
				}
			}
			is Lce.Content -> {
				when (result.packet) {
					is Result.ScreenLoadResult ->  currentViewState.copy()
					is Result.UsersResult -> currentViewState.copy(
						adapterList = result.packet.users,
						errorMessageVisibility = View.GONE,
						loadingStateVisibility = View.GONE)
				}
			}
		}
	}
}