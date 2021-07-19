package com.appscrip.trivia.ui.questions

import com.appscrip.trivia.data.db.entity.Question

/**
 * QuestionState holds the last state of the variable.
 */
sealed class QuestionState {

    /**
     * Initial state of the view.
     */
    object Idle : QuestionState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : QuestionState()

    /**
     * Success result of the view.
     */
    data class Success(val list: List<Question>) : QuestionState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : QuestionState()
}