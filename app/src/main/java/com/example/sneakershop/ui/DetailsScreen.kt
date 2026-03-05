package com.example.sneakershop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sneakershop.model.CartManager
import com.example.sneakershop.model.Sneaker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.size

@Composable
fun DetailsScreen(
    sneaker: Sneaker,
    onBack: () -> Unit
) {
    val sizes = (36..50).map { it.toString() }
    var selectedSize by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            AsyncImage(
                model = sneaker.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = sneaker.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${sneaker.price} $",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Size",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(sizes) { size ->
                        val isSelected = (selectedSize == size)

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    color = if (isSelected) Color.Black else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color.Transparent else Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selectedSize = if (selectedSize == size) "" else size
                                }
                        ) {
                            Text(
                                text = size,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }

                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                Text(
                    text = sneaker.description,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    lineHeight = 24.sp
                )
            }
        }

        val isButtonEnabled = selectedSize.isNotEmpty()

        Button(
            onClick = {
                CartManager.addToCart(sneaker)
                onBack()
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (isButtonEnabled) "Add to cart" else "Select size",
                fontSize = 18.sp
            )
        }
    }
}