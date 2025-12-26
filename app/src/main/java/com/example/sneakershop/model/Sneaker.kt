package com.example.sneakershop.model

data class Sneaker (
    val id: String, // unique number
    val name: String, // name of the sneaker
    val description: String, // description of the sneaker
    val price: Double, // price of the sneaker
    val imageUrl: String // link to the image
)