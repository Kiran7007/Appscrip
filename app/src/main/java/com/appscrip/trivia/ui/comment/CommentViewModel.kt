package com.appscrip.trivia.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appscrip.trivia.data.repository.CommentRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * CommentViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class CommentViewModel(private val repository: CommentRepository) : ViewModel() {

    companion object {
        private val TAG = CommentViewModel::class.java.simpleName
    }

    /**
     * Observes the intent.
     */
    val commentIntent = Channel<CommentIntent>(Channel.UNLIMITED)

    /**
     * Manage the states.
     */
    private val _state = MutableStateFlow<CommentState>(CommentState.Idle)
    val state: StateFlow<CommentState> get() = _state

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            commentIntent.consumeAsFlow().collect {
                /*when (it) {
                    is CommentIntent.FetchComment -> fetchDataFromRemote(it.url)
                }*/
            }
        }
    }
}