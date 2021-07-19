package com.appscrip.trivia.ui.questions

/**
 * QuestionIntent triggers the event to the viewModel.
 */
sealed class QuestionIntent {

    /**
     * Trigger the intent to fetch the data from local database.
     */
    data class FetchQuestions(val jsonString: String?) : QuestionIntent()

    /**
     * Trigger the intent to fetch the summary from local database.
     */
    object FetchSummary : QuestionIntent()

    /**
     * Trigger the intent to fetch the history from local database.
     */
    object FetchHistory : QuestionIntent()
}