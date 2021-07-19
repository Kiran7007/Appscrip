package com.appscrip.trivia.ui.questions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.data.repository.QuestionRepository
import com.appscrip.trivia.util.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class QuestionViewModelTest : TestCase() {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: QuestionRepository

    @Mock
    private lateinit var observer: Observer<List<Question>>

    @Before
    fun setup() {
    }
}