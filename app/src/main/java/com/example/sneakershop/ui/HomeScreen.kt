package com.example.sneakershop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import com.example.sneakershop.model.SneakerData
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults

@Composable
fun HomeScreen(
    onSneakerClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val categories = listOf("All", "Nike", "Adidas", "Puma")
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember {mutableStateOf("")}
    val filteredSneakers = SneakerData.list.filter { sneaker ->
        val matchCategory = if(selectedCategory == "All") true else sneaker.name.startsWith(selectedCategory)
        val matchText = sneaker.name.contains(searchQuery, ignoreCase = true)
        matchCategory && matchText
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sneaker Shop",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                newText ->
                searchQuery = newText
            },
            label = {Text("Search")},
            leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(categories) {
                category ->
                Button(onClick = {selectedCategory = category},
                    colors = ButtonDefaults.buttonColors(containerColor = if(selectedCategory == category) Color.Black else Color.LightGray, contentColor = Color.White),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(category)
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(filteredSneakers) {
                filteredSneakers ->
                SneakerItem(
                    sneaker = filteredSneakers,
                    onClick = { onSneakerClick(filteredSneakers.id) }
                )
            }
        }
    }
}