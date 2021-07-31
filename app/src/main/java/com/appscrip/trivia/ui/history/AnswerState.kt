package com.appscrip.trivia.ui.history

import com.appscrip.trivia.data.db.entity.Answer

/**
 * AnswerState holds the last state of the variable.
 */
sealed class AnswerState {

    /**
     * Initial state of the view.
     */
    object Idle : AnswerState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : AnswerState()

    /**
     * Success result of the view.
     */
    data class Success(val list: List<Answer>) : AnswerState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : AnswerState()
}