package com.har.onecommitaday.extensions

fun Int.getTreeEmoji(): String {
    return when {
        this in 1 until 4 -> "🌱"
        this in 4 until 10 -> "🌿"
        this in 10 until 100 -> "🌳"
        this >= 100 -> "🎄"
        else -> "🔥"
    }
}

fun Int.getFlowerEmoji(): String {
    return when {
        this in 1 until 4 -> "🌱"
        this in 4 until 10 -> "🌿"
        this in 10 until 100 -> "🌹"
        this >= 100 -> "💐"
        else -> "🥀"
    }
}
