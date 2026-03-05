package com.example.sneakershop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sneakers_table")
data class Sneaker(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
)
