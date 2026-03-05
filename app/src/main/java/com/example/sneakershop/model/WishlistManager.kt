package com.example.sneakershop.model

import androidx.compose.runtime.mutableStateListOf

object WishlistManager {
    val favoriteItems = mutableStateListOf<Sneaker>()

    fun add(sneaker: Sneaker) {
        if (!contains(sneaker)) {
            favoriteItems.add(sneaker)
        }
    }

    fun remove(sneaker: Sneaker) {
        favoriteItems.removeAll { it.id == sneaker.id }
    }

    fun contains(sneaker: Sneaker): Boolean {
        return favoriteItems.any { it.id == sneaker.id }
    }
}
