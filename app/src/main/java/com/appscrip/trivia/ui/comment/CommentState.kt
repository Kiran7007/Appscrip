package com.appscrip.trivia.ui.comment

import com.appscrip.trivia.data.db.entity.Comment

/**
 * CommentState holds the last state of the variable.
 */
sealed class CommentState {

    /**
     * Initial state of the view.
     */
    object Idle : CommentState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : CommentState()

    /**
     * Success result of the view.
     */
    data class Success(val list: List<Comment>) : CommentState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : CommentState()
}