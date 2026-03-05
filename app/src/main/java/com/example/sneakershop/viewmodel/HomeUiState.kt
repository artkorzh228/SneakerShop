package com.example.sneakershop.viewmodel

import com.example.sneakershop.model.Sneaker

data class HomeUiState(
    val sneakers: List<Sneaker> = emptyList(),
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val categories: List<String> = listOf("All", "Nike", "Adidas", "Puma")
)
