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
import com.example.sneakershop.ui.CartScreen
import com.example.sneakershop.ui.CheckoutScreen
import com.example.sneakershop.ui.WishlistScreen
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.example.sneakershop.ui.SplashScreen
import com.example.sneakershop.ui.ProfileScreen
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import com.example.sneakershop.model.CartManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute in listOf("home", "wishlist", "cart", "profile")

            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        NavigationBar(containerColor = Color.White) {
                            val items = listOf(
                                Triple("home", "Home", Icons.Default.Home),
                                Triple("wishlist", "Wishlist", Icons.Default.Favorite),
                                Triple("cart", "Cart", Icons.Default.ShoppingCart),
                                Triple("profile", "Profile", Icons.Default.Person)
                            )
                            items.forEach { (route, label, icon) ->
                                NavigationBarItem(
                                    icon = {

                                        if (route == "cart" && CartManager.cartItems.isNotEmpty()) {
                                            BadgedBox(
                                                badge = {
                                                    Badge {

                                                        Text(text = CartManager.cartItems.size.toString())
                                                    }
                                                }
                                            ) {
                                                Icon(icon, contentDescription = label)
                                            }
                                        } else {
                                            Icon(icon, contentDescription = label)
                                        }
                                    },
                                    label = { Text(label) },
                                    selected = currentRoute == route,
                                    onClick = {
                                        navController.navigate(route) { launchSingleTop = true }
                                    },
                                    colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.LightGray.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                    }
                }
            ) {
                innerPadding ->

                NavHost(navController = navController, startDestination = "splash", modifier = Modifier.padding(innerPadding)) {

                    composable("splash") {
                        SplashScreen (
                            onTimeout = {
                                navController.navigate("home") {
                                    popUpTo("splash") {inclusive = true}
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            onSneakerClick = { sneakerId -> navController.navigate("details/$sneakerId") }
                        )
                    }

                    composable(
                        route = "details/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { backStackEntry ->

                        val id = backStackEntry.arguments?.getString("id")

                        val sneaker = SneakerData.list.find { it.id == id }

                        if (sneaker != null) {
                            DetailsScreen(sneaker = sneaker, onBack = {navController.popBackStack() }
                            )
                        }
                    }

                    composable("cart") {
                        CartScreen(
                            onCheckout = { navController.navigate("checkout") }
                        )
                    }

                    composable("checkout") {
                        CheckoutScreen(
                            onBackToHome = {
                                navController.navigate("home") {
                                    popUpTo("home") {inclusive = true}
                                }
                            }
                        )
                    }
                    composable("wishlist") {
                        WishlistScreen(
                            onSneakerClick = { sneakerId -> navController.navigate("details/$sneakerId") }
                        )
                    }

                    composable("profile") {
                        ProfileScreen()
                    }
                }
            }
        }
    }
}
