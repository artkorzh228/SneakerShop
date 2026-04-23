package com.example.sneakershop.viewmodel

import com.example.sneakershop.model.Sneaker

enum class SortOrder { DEFAULT, PRICE_ASC, PRICE_DESC }

data class HomeUiState(
    val sneakers: List<Sneaker> = emptyList(),
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val categories: List<String> = listOf("All", "Nike", "Adidas", "Jordan", "New Balance", "Puma"),
    val priceMin: Float = 0f,
    val priceMax: Float = 300f,
    val sortOrder: SortOrder = SortOrder.DEFAULT
)
