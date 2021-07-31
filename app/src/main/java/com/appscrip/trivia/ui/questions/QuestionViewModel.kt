package com.appscrip.trivia.ui.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appscrip.trivia.data.db.entity.Answer
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.data.repository.QuestionRepository
import com.appscrip.trivia.ui.history.AnswerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

/**
 * IssueViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {

    companion object {
        private val TAG = QuestionViewModel::class.java.simpleName
    }

    val answerList = ArrayList<Answer>()

    /**
     * Observes the intent.
     */
    val questionIntent = Channel<QuestionIntent>(Channel.UNLIMITED)

    /**
     * Manage the question states.
     */
    private val _questionState = MutableStateFlow<QuestionState>(QuestionState.Idle)
    val questionState: StateFlow<QuestionState> get() = _questionState

    /**
     * Manage the summary states.
     */
    private val _summaryState = MutableStateFlow<QuestionState>(QuestionState.Idle)
    val summaryState: StateFlow<QuestionState> get() = _summaryState

    /**
     * Manage the history states.
     */
    private val _historyState = MutableStateFlow<AnswerState>(AnswerState.Idle)
    val historyState: StateFlow<AnswerState> get() = _historyState

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            questionIntent.consumeAsFlow().collect {
                when (it) {
                    is QuestionIntent.FetchQuestions -> fetchQuestionsFromLocal(it.jsonString)
                    is QuestionIntent.FetchSummary -> fetchSummaryFromLocal()
                    is QuestionIntent.FetchHistory -> fetchHistoryFromLocal()
                }
            }
        }
    }

    /**
     * Gets the questions data from the local database.
     */
    private fun fetchQuestionsFromLocal(jsonString: String?) {
        viewModelScope.launch {
            repository.fetchDataFromDB().collect {
                _questionState.value = QuestionState.Success(it)

                if (it.isNullOrEmpty() && !jsonString.isNullOrEmpty()) {
                    withContext(Dispatchers.IO) {
                        val jsonArray = JSONArray(jsonString)
                        val questionList = ArrayList<Question>()
                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val question = Question.fromJson(item)
                            questionList.add(question)
                        }
                        questionList.add(
                            Question(
                                id = questionList.size.toLong() + 1,
                                type = "summary"
                            )
                        )
                        repository.insert(questionList)
                    }
                }
            }
        }
    }

    /**
     * Gets the summary data from the local database.
     */
    private fun fetchSummaryFromLocal() {
        viewModelScope.launch {
            repository.fetchDataFromDB().collect {
                if (!it.isNullOrEmpty()) {
                    _summaryState.value = QuestionState.Success(it)
                } else {
                    //fetchDataFromRemote()
                }
            }
        }
    }

    /**
     * Gets the history data from the local database.
     */
    private fun fetchHistoryFromLocal() {
        viewModelScope.launch {
            repository.fetchAnswersFromDB().collect {
                if (!it.isNullOrEmpty()) {
                    _historyState.value = AnswerState.Success(it)
                } else {
                    //fetchDataFromRemote()
                }
            }
        }
    }

    fun saveAnswer(answer: Answer) {

    }

    fun submitAnswer(result: Answer) {

    }
}