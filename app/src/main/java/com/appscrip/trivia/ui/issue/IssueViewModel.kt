package com.appscrip.trivia.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appscrip.trivia.data.repository.IssueRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * IssueViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class IssueViewModel(private val repository: IssueRepository) : ViewModel() {

    companion object {
        private val TAG = IssueViewModel::class.java.simpleName
    }

    /**
     * Observes the intent.
     */
    val issueIntent = Channel<IssueIntent>(Channel.UNLIMITED)

    /**
     * Manage the states.
     */
    private val _state = MutableStateFlow<IssueState>(IssueState.Idle)
    val state: StateFlow<IssueState> get() = _state

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            issueIntent.consumeAsFlow().collect {
                when (it) {
                    is IssueIntent.FetchLocalIssue -> fetchDataFromLocal()
                }
            }
        }
    }

    /**
     * Gets the data from the local database.
     */
    private fun fetchDataFromLocal() {
        viewModelScope.launch {
            repository.fetchDataFromDB().collect {
                if (!it.isNullOrEmpty()) {
                    _state.value = IssueState.Success(it)
                } else {
                    //fetchDataFromRemote()
                }
            }
        }
    }
}