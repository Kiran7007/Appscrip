package com.appscrip.trivia.ui.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appscrip.trivia.R
import com.appscrip.trivia.adapters.QuestionAdapter
import com.appscrip.trivia.data.db.entity.Answer
import com.appscrip.trivia.databinding.QuestionFragmentBinding
import com.appscrip.trivia.util.getQuestions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * IssueFragment shows the people list.
 */
class QuestionFragment : Fragment() {

    /**
     * IssueViewModel injected bu dependency injection.
     */
    private val viewModel by sharedViewModel<QuestionViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: QuestionFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QuestionFragmentBinding.inflate(inflater)
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
        adapter = QuestionAdapter(viewModel)
        binding.viewpager.adapter = adapter
        binding.viewpager.isUserInputEnabled = false
        binding.btNext.setOnClickListener {
            var currentItem = binding.viewpager.currentItem

            if (currentItem < adapter.itemCount) {
                if (binding.btNext.text.toString().equals(getString(R.string.finish), false)) {
                    viewModel.submitAnswer(Answer())
                    binding.viewpager.currentItem = 0
                    currentItem = 0
                } else {
                    binding.viewpager.currentItem = ++currentItem
                    viewModel.saveAnswer(Answer())
                }
            }
            if (currentItem == adapter.itemCount - 2) {
                binding.btNext.text = getString(R.string.finish)
            } else {
                binding.btNext.text = getString(R.string.next)
            }
        }

        binding.btHistory.setOnClickListener {
            val action = QuestionFragmentDirections.actionQuestionFragmentToHistoryFragment()
            findNavController().navigate(action);
        }
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.questionState.collect {
                when (it) {
                    is QuestionState.Idle -> {
                        viewModel.questionIntent.send(
                            QuestionIntent.FetchQuestions(
                                requireActivity().getQuestions(
                                    "questions.json"
                                )
                            )
                        )
                    }
                    is QuestionState.Loading -> {
                        if (it.isLoading) {
                            setViewVisibility(View.GONE, View.GONE, View.VISIBLE)
                        } else {
                            setViewVisibility(View.VISIBLE, View.GONE, View.GONE)
                        }
                    }
                    is QuestionState.Success -> {
                        adapter.submitList(it.list)
                    }
                    is QuestionState.Error -> {
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
        binding.viewpager.visibility = recyclerView
        binding.btNext.visibility = recyclerView
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