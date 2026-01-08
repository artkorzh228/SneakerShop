package com.example.sneakershop.model

import androidx.compose.runtime.mutableStateListOf

object CartManager {
    val cartItems = mutableStateListOf<Sneaker>()

    fun addToCart(sneaker: Sneaker){
        cartItems.add(sneaker)
    }

    fun removeFromCart(sneaker: Sneaker){
        cartItems.remove(sneaker)
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }

    fun clearCart(){
        cartItems.clear()
    }
}