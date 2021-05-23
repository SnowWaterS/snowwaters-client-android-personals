package com.har.onecommitaday.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.har.onecommitaday.api.model.CommitHistory
import com.har.onecommitaday.databinding.JandiCommitHistoryItemBinding

class JandiCommitHistoryAdapter : RecyclerView.Adapter<JandiCommitHistoryViewHolder>() {

    private val commitHistoryList: MutableList<CommitHistory> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JandiCommitHistoryViewHolder {
        val binding = JandiCommitHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JandiCommitHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JandiCommitHistoryViewHolder, position: Int) {
        holder.bind(commitHistoryList[position])
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

class JandiCommitHistoryViewHolder(private val binding: JandiCommitHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(commitHistory: CommitHistory) {
        binding.tvCommitHistory.text = commitHistory.getJandiString()
    }
}