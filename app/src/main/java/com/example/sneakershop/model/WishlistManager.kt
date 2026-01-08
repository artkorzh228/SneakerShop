package com.example.sneakershop.model

object WishlistManager{
    val favoriteItems = mutableListOf<Sneaker>()

    fun add(sneaker: Sneaker){
        if(!contains(sneaker)){
            favoriteItems.add(sneaker)
        }

    }

    fun remove(sneaker: Sneaker){
        favoriteItems.removeAll{it.id == sneaker.id}
    }

    fun contains(sneaker: Sneaker): Boolean{
        return favoriteItems.any{it.id == sneaker.id}
    }
}