package com.example.sneakershop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sneakershop.R
import com.example.sneakershop.model.CartManager
import com.example.sneakershop.model.Sneaker

@Composable
fun CartScreen(onCheckout: () -> Unit) {
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
                Text("Your cart is empty :(", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems, key = { it.first.id }) { (sneaker, quantity) ->
                    CartItemRow(
                        sneaker = sneaker,
                        quantity = quantity,
                        onAdd = { CartManager.addToCart(sneaker) },
                        onRemove = { CartManager.removeFromCart(sneaker) }
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
                        text = "${"%.2f".format(CartManager.getTotalPrice())} $",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    onClick = {
                        CartManager.clearCart()
                        onCheckout()
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Text("Place an order")
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    sneaker: Sneaker,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    val context = LocalContext.current
    val resId = remember(sneaker.imageUrl) {
        context.resources.getIdentifier(sneaker.imageUrl, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (resId != 0) painterResource(resId) else painterResource(R.drawable.ic_launcher_background),
                contentDescription = sneaker.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sneaker.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${"%.2f".format(sneaker.price)} $",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Уменьшить",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onAdd, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Увеличить",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
