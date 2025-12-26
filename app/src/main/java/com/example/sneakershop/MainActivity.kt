package com.example.sneakershop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sneakershop.model.SneakerData
import com.example.sneakershop.ui.SneakerItem
import androidx.compose.ui.unit.dp
import com.example.sneakershop.ui.DetailsScreen
import com.example.sneakershop.ui.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 1. Создаем контроллер навигации (наш GPS)
            val navController = rememberNavController()

            // 2. Настраиваем карту маршрутов (NavHost)
            // startDestination — с чего начинать (конечно, с витрины)
            NavHost(navController = navController, startDestination = "home") {

                // Экран 1: Витрина
                composable("home") {
                    HomeScreen(
                        onSneakerClick = { sneakerId ->
                            // Когда нажали на кроссовок, летим на экран деталей
                            // и передаем ID в адресе, как в браузере: details/1
                            navController.navigate("details/$sneakerId")
                        }
                    )
                }

                // Экран 2: Детали
                // {id} — это переменная часть адреса
                composable(
                    route = "details/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) { backStackEntry ->
                    // Получаем ID из адреса
                    val id = backStackEntry.arguments?.getString("id")

                    // Ищем кроссовок в нашей базе данных по этому ID
                    val sneaker = SneakerData.list.find { it.id == id }

                    // Если нашли — показываем экран
                    if (sneaker != null) {
                        DetailsScreen(sneaker = sneaker)
                    }
                }
            }
        }
    }
}
