package com.example.sneakershop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Профиль",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 32.dp, top = 16.dp)
                .align(Alignment.Start)
        )


        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/licensed-image?q=tbn:ANd9GcTyhgwFBBuEqiLB5BAjQR4hCDCoJYefwwYtelRMap_8uXFoyisZLRptYiqLuXet0zX9X9Z4z_UAxYbYCcyD9Pm8i2iEe1ljOiYaXfrieWMo7cAQCVQZQ8iYoWz5pDdJFY67SAOckK9jv-c&s=19",
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(24.dp))


        ProfileItem(icon = Icons.Default.Person, title = "Name", value = "Alex Sneaker")
        ProfileItem(icon = Icons.Default.Call, title = "Phone", value = "+7 999 000-00-00")
        ProfileItem(icon = Icons.Default.Email, title = "Email", value = "alex@sneaker.shop")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { TODO() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f))
        ) {
            Text("Log out")
        }
    }
}


@Composable
fun ProfileItem(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
    Divider(color = Color.LightGray.copy(alpha = 0.3f))
}