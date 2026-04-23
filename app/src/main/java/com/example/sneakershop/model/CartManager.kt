package com.example.sneakershop.model

import androidx.compose.runtime.mutableStateMapOf

object CartManager {
    private val cartMap = mutableStateMapOf<String, Pair<Sneaker, Int>>()

    val cartItems: List<Pair<Sneaker, Int>>
        get() = cartMap.values.toList()

    fun addToCart(sneaker: Sneaker) {
        val qty = cartMap[sneaker.id]?.second ?: 0
        cartMap[sneaker.id] = sneaker to qty + 1
    }

    fun removeFromCart(sneaker: Sneaker) {
        val qty = cartMap[sneaker.id]?.second ?: return
        if (qty > 1) cartMap[sneaker.id] = sneaker to qty - 1
        else cartMap.remove(sneaker.id)
    }

    fun getQuantity(sneakerId: String): Int = cartMap[sneakerId]?.second ?: 0

    fun getTotalPrice(): Double =
        cartMap.values.sumOf { (sneaker, qty) -> sneaker.price * qty }

    fun getTotalQuantity(): Int = cartMap.values.sumOf { it.second }

    fun clearCart() = cartMap.clear()
}
