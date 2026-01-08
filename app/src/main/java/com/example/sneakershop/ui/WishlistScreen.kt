package com.example.sneakershop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sneakershop.model.WishlistManager

@Composable
fun WishlistScreen(
    onSneakerClick: (String) -> Unit
) {
    val favorites = WishlistManager.favoriteItems

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Favorites",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 24.dp)
        )
        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing in favorites :(", fontSize = 20.sp, color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ){
                items(favorites) { sneaker ->

                    SneakerItem(
                        sneaker = sneaker,
                        onClick = {
                            onSneakerClick(sneaker.id)
                        }
                    )
                }
            }
        }
    }
}