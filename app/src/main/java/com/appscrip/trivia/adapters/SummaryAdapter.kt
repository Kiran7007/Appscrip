package com.appscrip.trivia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.trivia.data.db.entity.Question
import com.appscrip.trivia.databinding.LayoutQuestionItemBinding
import com.appscrip.trivia.ui.questions.QuestionViewModel

/**
 * CommentAdapter is responsible to covert question data into view by binding comment model with the view.
 */
class SummaryAdapter(private val viewModel: QuestionViewModel) :
    ListAdapter<Question, SummaryAdapter.ViewHolder>(SummaryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    class ViewHolder(private val binding: LayoutQuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the question model with the view.
         */
        fun bind(item: Question?, viewModel: QuestionViewModel) {
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
                val binding = LayoutQuestionItemBinding.inflate(
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
    class SummaryDiffCallBack : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Question, newItem: Question) = oldItem == newItem
    }
}


