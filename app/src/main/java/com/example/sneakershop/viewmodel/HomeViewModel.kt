package com.example.sneakershop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sneakershop.model.Sneaker
import com.example.sneakershop.model.SneakerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Первоначальная загрузка с дефолтными фильтрами
        _uiState.update { current ->
            current.copy(sneakers = filterSneakers(current.selectedCategory, current.searchQuery))
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { current ->
            current.copy(
                selectedCategory = category,
                sneakers = filterSneakers(category, current.searchQuery)
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { current ->
            current.copy(
                searchQuery = query,
                sneakers = filterSneakers(current.selectedCategory, query)
            )
        }
    }

    private fun filterSneakers(category: String, searchQuery: String): List<Sneaker> =
        SneakerData.list.filter { sneaker ->
            val matchCategory = category == "All" || sneaker.category == category
            val matchSearch = sneaker.name.contains(searchQuery, ignoreCase = true)
            matchCategory && matchSearch
        }
}
