package com.example.sneakershop.model

import androidx.compose.runtime.mutableStateListOf

object RecentlyViewedManager {
    val items = mutableStateListOf<Sneaker>()

    fun add(sneaker: Sneaker) {
        items.removeAll { it.id == sneaker.id }
        items.add(0, sneaker)
        if (items.size > 5) items.removeAt(items.lastIndex)
    }
}
