package com.appscrip.trivia.ui.issue

import com.appscrip.trivia.data.db.entity.Issue

/**
 * IssueState holds the last state of the variable.
 */
sealed class IssueState {

    /**
     * Initial state of the view.
     */
    object Idle : IssueState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : IssueState()

    /**
     * Success result of the view.
     */
    data class Success(val list: List<Issue>) : IssueState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : IssueState()
}