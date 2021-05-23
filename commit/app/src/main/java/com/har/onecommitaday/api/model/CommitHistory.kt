package com.har.onecommitaday.api.model

import com.har.onecommitaday.extensions.getFlowerEmoji
import com.har.onecommitaday.extensions.getTreeEmoji
import java.time.LocalDate

data class CommitHistory(
    val date: LocalDate,
    val count: Int
) {
    fun getJandiString(): String {
        return "$date (${date.dayOfWeek.name.substring(0, 3)}) - ${count.getTreeEmoji()} $count"
    }

    fun getFlowerBedString(): String {
        return count.getFlowerEmoji()
    }
}
