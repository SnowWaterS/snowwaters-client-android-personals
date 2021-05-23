package com.har.onecommitaday.api.model

import com.har.onecommitaday.extensions.getTreeEmoji

data class CommitSummary(
        var consecutiveDays: Int = 0,
        var emoji: String = consecutiveDays.getTreeEmoji()
) {
}