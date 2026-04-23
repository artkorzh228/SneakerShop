package com.example.sneakershop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakershop.model.Sneaker
import com.example.sneakershop.model.SneakerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { current ->
            current.copy(sneakers = filterSneakers(current.selectedCategory, current.searchQuery, current.priceMin, current.priceMax, current.sortOrder))
        }
    }

    fun onCategorySelected(category: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val state = _uiState.value
            val filtered = filterSneakers(category, state.searchQuery, state.priceMin, state.priceMax, state.sortOrder)
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(selectedCategory = category, sneakers = filtered) }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val state = _uiState.value
            val filtered = filterSneakers(state.selectedCategory, query, state.priceMin, state.priceMax, state.sortOrder)
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(searchQuery = query, sneakers = filtered) }
            }
        }
    }

    fun onPriceRangeChanged(min: Float, max: Float) {
        viewModelScope.launch(Dispatchers.Default) {
            val state = _uiState.value
            val filtered = filterSneakers(state.selectedCategory, state.searchQuery, min, max, state.sortOrder)
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(priceMin = min, priceMax = max, sneakers = filtered) }
            }
        }
    }

    fun onSortOrderChanged(order: SortOrder) {
        viewModelScope.launch(Dispatchers.Default) {
            val state = _uiState.value
            val filtered = filterSneakers(state.selectedCategory, state.searchQuery, state.priceMin, state.priceMax, order)
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(sortOrder = order, sneakers = filtered) }
            }
        }
    }

    private fun filterSneakers(
        category: String,
        searchQuery: String,
        priceMin: Float,
        priceMax: Float,
        sortOrder: SortOrder = SortOrder.DEFAULT
    ): List<Sneaker> {
        val filtered = SneakerData.list.filter { sneaker ->
            val matchCategory = category == "All" || sneaker.category == category
            val matchSearch = sneaker.name.contains(searchQuery, ignoreCase = true)
            val matchPrice = sneaker.price >= priceMin && sneaker.price <= priceMax
            matchCategory && matchSearch && matchPrice
        }
        return when (sortOrder) {
            SortOrder.PRICE_ASC -> filtered.sortedBy { it.price }
            SortOrder.PRICE_DESC -> filtered.sortedByDescending { it.price }
            SortOrder.DEFAULT -> filtered
        }
    }
}
