package com.appscrip.trivia.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.databinding.*
import com.appscrip.trivia.ui.questions.QuestionViewModel

private const val SIMPLE = 1
private const val SINGLE_CHOICE = 2
private const val MULTI_CHOICE = 3
private const val SUMMARY = 4
private const val HISTORY = 5

/**
 * QuestionAdapter is responsible to covert question data into view by binding Issue model with the view.
 */
class QuestionAdapter(private val viewModel: QuestionViewModel) :
    ListAdapter<Question, QuestionAdapter.ViewHolder>(QuestionDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SINGLE_CHOICE -> SingleChoiceViewHolder.from(parent)
            MULTI_CHOICE -> MultiChoiceViewHolder.from(parent)
            SUMMARY -> SummaryViewHolder.from(parent)
            HISTORY -> HistoryViewHolder.from(parent)
            else -> SimpleViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            "single" -> SINGLE_CHOICE
            "multi" -> MULTI_CHOICE
            "summary" -> SUMMARY
            "history" -> HISTORY
            else -> SIMPLE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    abstract class ViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the question model with the view.
         */
        abstract fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)? = null
        )
    }

    private class SimpleViewHolder(private val binding: LayoutSimpleQuestionItemBinding) :
        ViewHolder(binding) {

        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): SimpleViewHolder {
                val binding = LayoutSimpleQuestionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SimpleViewHolder(binding)
            }
        }

        override fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)?
        ) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
                binding.etAnswers.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        viewModel.storeTextResult(p0?.toString())
                    }

                    override fun afterTextChanged(p0: Editable?) {}
                })
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }
    }

    private class SingleChoiceViewHolder(private val binding: LayoutSingleChoiceQuestionItemBinding) :
        ViewHolder(binding) {

        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): SingleChoiceViewHolder {
                val binding = LayoutSingleChoiceQuestionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SingleChoiceViewHolder(binding)
            }
        }

        override fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)?
        ) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel

                it.options.forEachIndexed { index, option ->
                    val radioButton = RadioButton(binding.root.context)
                    radioButton.id = index
                    radioButton.text = option
                    binding.rbOptions.addView(radioButton)
                }
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }
    }

    private class MultiChoiceViewHolder(private val binding: LayoutMultiChoiceQuestionItemBinding) :
        ViewHolder(binding) {

        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): MultiChoiceViewHolder {
                val binding = LayoutMultiChoiceQuestionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MultiChoiceViewHolder(binding)
            }
        }

        override fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)?
        ) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel

                it.options.forEachIndexed { index, option ->
                    val checkBox = AppCompatCheckBox(binding.root.context)
                    checkBox.id = index
                    checkBox.text = option
                    binding.llOptions.addView(checkBox)
                }
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }
    }

    private class SummaryViewHolder(private val binding: LayoutSummaryItemBinding) :
        ViewHolder(binding) {

        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): SummaryViewHolder {
                val binding = LayoutSummaryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SummaryViewHolder(binding)
            }
        }

        override fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)?
        ) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }
    }

    private class HistoryViewHolder(private val binding: LayoutHistoryItemBinding) :
        ViewHolder(binding) {

        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): HistoryViewHolder {
                val binding = LayoutHistoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HistoryViewHolder(binding)
            }
        }

        override fun bind(
            item: Question?,
            viewModel: QuestionViewModel,
            onItemClick: ((Question) -> Unit)?
        ) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }
    }

    /**
     * QuestionDiffCallBack replace only those items in the list which is updated.
     */
    class QuestionDiffCallBack : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Question, newItem: Question) = oldItem == newItem
    }
}


