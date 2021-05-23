package com.har.onecommitaday.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.har.onecommitaday.api.model.CommitHistory
import com.har.onecommitaday.databinding.FlowerBedCommitHistoryItemBinding

class FlowerBedCommitHistoryAdapter(
        private val onSelected: (position: Int, commitHistory: CommitHistory) -> Unit
) : RecyclerView.Adapter<FlowerBedCommitHistoryViewHolder>() {

    private val commitHistoryList: MutableList<CommitHistory> = mutableListOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): FlowerBedCommitHistoryViewHolder {
        val binding = FlowerBedCommitHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlowerBedCommitHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlowerBedCommitHistoryViewHolder, position: Int) {
        holder.bind(commitHistoryList[position], onSelected)
    }

    override fun getItemCount(): Int {
        return commitHistoryList.size
    }

    fun setContributions(contributions: List<CommitHistory>) {
        this.commitHistoryList.clear()
        this.commitHistoryList.addAll(contributions)
        notifyDataSetChanged()
    }
}

class FlowerBedCommitHistoryViewHolder(private val binding: FlowerBedCommitHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(commitHistory: CommitHistory, onSelected: (position: Int, commitHistory: CommitHistory) -> Unit) {
        binding.tvCommitHistory.text = commitHistory.getFlowerBedString()
        binding.root.setOnClickListener {
            onSelected(bindingAdapterPosition, commitHistory)
        }
    }
}