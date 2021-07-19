package com.appscrip.trivia.ui.issue

/**
 * IssueIntent triggers the event to the viewModel.
 */
sealed class IssueIntent {

    /**
     * Trigger the intent to fetch the data from local database.
     */
    object FetchLocalIssue : IssueIntent()
}