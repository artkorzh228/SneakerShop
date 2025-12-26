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
import com.example.sneakershop.model.Sneaker

@Composable
fun DetailsScreen(sneaker: Sneaker) {
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
                   text = "Описание",
                   fontSize = 18.sp,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
               )
               Text(
                   text = sneaker.description,
                   fontSize = 16.sp,
                   color = Color.Black,
                   lineHeight = 24.sp
               )
           }
       }

        Button(
            onClick = { /* Пока пусто */ },
            modifier = Modifier
                .align(Alignment.BottomCenter) // Прилепить вниз
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp), // Высокая удобная кнопка
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Добавить в корзину", fontSize = 18.sp)
        }
    }
}