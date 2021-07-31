package com.appscrip.trivia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.trivia.data.db.entity.Answer
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.databinding.LayoutHistoryItemBinding
import com.appscrip.trivia.databinding.LayoutSummaryItemBinding
import com.appscrip.trivia.ui.questions.QuestionViewModel

/**
 * HistoryAdapter is responsible to covert question data into view by binding comment model with the view.
 */
class HistoryAdapter(private val viewModel: QuestionViewModel) :
    ListAdapter<Answer, HistoryAdapter.ViewHolder>(AnswerDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    class ViewHolder(private val binding: LayoutHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the question model with the view.
         */
        fun bind(item: Answer?, viewModel: QuestionViewModel) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
            }
        }

        /**
         * Methods and variables in companion object are static.
         */
        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): ViewHolder {
                val binding = LayoutHistoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    /**
     * SummaryDiffCallBack replace only those items in the list which is updated.
     */
    class AnswerDiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer) =
            oldItem.timestamp == newItem.timestamp

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer) = oldItem == newItem
    }
}


