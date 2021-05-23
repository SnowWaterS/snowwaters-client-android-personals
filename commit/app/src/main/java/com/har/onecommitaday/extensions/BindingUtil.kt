package com.har.onecommitaday.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.har.onecommitaday.api.model.CommitHistory
import com.har.onecommitaday.manager.SettingManager
import com.har.onecommitaday.ui.main.FlowerBedCommitHistoryAdapter
import com.har.onecommitaday.ui.main.JandiCommitHistoryAdapter

object BindingUtil {

    @JvmStatic
    @BindingAdapter("contributions")
    fun bindContributions(recyclerView: RecyclerView, contributions: List<CommitHistory>?) {
        val typeAppearacne = SettingManager.getApperanceType(recyclerView.context)
        if (typeAppearacne == "jandi") {
            val adapter = recyclerView.adapter as? JandiCommitHistoryAdapter
            contributions?.let { adapter?.setContributions(it) }
        }

        if (typeAppearacne == "flower") {
            val adapter = recyclerView.adapter as? FlowerBedCommitHistoryAdapter
            contributions?.let { adapter?.setContributions(it) }
        }
    }
}