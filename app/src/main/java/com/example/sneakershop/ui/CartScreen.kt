package com.example.sneakershop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sneakershop.model.CartManager


@Composable
fun CartScreen(
    onCheckout: () -> Unit
) {
    val cartItems = CartManager.cartItems

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Cart",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 24.dp)
        )
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty :(", fontSize = 20.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    SneakerItem(
                        sneaker = item,
                        onClick = {},
                        showDeleteButton = true,
                        onDeleteClick = { CartManager.removeFromCart(item) }
                    )

                }
            }

            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${CartManager.getTotalPrice()} $",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                }

                Button(
                    onClick = { CartManager.clearCart()
                                onCheckout()
                              },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Place an order")
                }
            }
        }
    }
}