package com.appscrip.trivia.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.appscrip.trivia.adapters.HistoryAdapter
import com.appscrip.trivia.databinding.HistoryFragmentBinding
import com.appscrip.trivia.ui.questions.QuestionIntent
import com.appscrip.trivia.ui.questions.QuestionViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * HistoryFragment shows the history.
 */
class HistoryFragment : Fragment() {

    /**
     * QuestionViewModel injected bu dependency injection.
     */
    private val viewModel by sharedViewModel<QuestionViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: HistoryFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerState()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        adapter = HistoryAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.historyState.collect {
                when (it) {
                    is AnswerState.Idle -> {
                        viewModel.questionIntent.send(QuestionIntent.FetchHistory)
                    }
                    is AnswerState.Loading -> {
                        if (it.isLoading) {
                            setViewVisibility(View.GONE, View.GONE, View.VISIBLE)
                        } else {
                            setViewVisibility(View.VISIBLE, View.GONE, View.GONE)
                        }
                    }
                    is AnswerState.Success -> {
                        adapter.submitList(it.list)
                    }
                    is AnswerState.Error -> {
                        setViewVisibility(View.GONE, View.VISIBLE, View.GONE)
                        it.message?.let { message -> shoToast(message) }
                    }
                }
            }
        }
    }

    /**
     * Update view visibility.
     */
    private fun setViewVisibility(recyclerView: Int, emptyText: Int, progressBar: Int) {
        binding.recyclerView.visibility = recyclerView
        binding.tvEmpty.visibility = emptyText
        binding.progressCircular.visibility = progressBar
    }

    /**
     * Shows message.
     */
    private fun shoToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}