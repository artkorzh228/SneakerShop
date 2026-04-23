package com.example.sneakershop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sneakershop.model.CartManager
import com.example.sneakershop.model.RecentlyViewedManager
import com.example.sneakershop.model.SneakerData
import com.example.sneakershop.ui.CartScreen
import com.example.sneakershop.ui.CheckoutScreen
import com.example.sneakershop.ui.DetailsScreen
import com.example.sneakershop.ui.HomeScreen
import com.example.sneakershop.ui.OnboardingScreen
import com.example.sneakershop.ui.ProfileScreen
import com.example.sneakershop.ui.SplashScreen
import com.example.sneakershop.ui.WishlistScreen
import com.example.sneakershop.ui.theme.SneakerShopTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_mode", false)
        val onboardingShown = prefs.getBoolean("onboarding_shown", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SneakerShopTheme(darkTheme = isDark) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf("home", "wishlist", "cart", "profile")

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                                val items = listOf(
                                    Triple("home", "Home", Icons.Default.Home),
                                    Triple("wishlist", "Wishlist", Icons.Default.Favorite),
                                    Triple("cart", "Cart", Icons.Default.ShoppingCart),
                                    Triple("profile", "Profile", Icons.Default.Person)
                                )
                                items.forEach { (route, label, icon) ->
                                    NavigationBarItem(
                                        icon = {
                                            val totalQty = CartManager.getTotalQuantity()
                                            if (route == "cart") {
                                                BadgedBox(
                                                    badge = {
                                                        if (totalQty > 0) {
                                                            Badge(
                                                                containerColor = MaterialTheme.colorScheme.error,
                                                                contentColor = MaterialTheme.colorScheme.onError
                                                            ) {
                                                                Text(
                                                                    text = if (totalQty > 99) "99+" else totalQty.toString(),
                                                                    fontSize = 10.sp
                                                                )
                                                            }
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
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (onboardingShown) "splash" else "onboarding",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                onFinish = {
                                    prefs.edit().putBoolean("onboarding_shown", true).apply()
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("splash") {
                            SplashScreen(
                                onTimeout = {
                                    navController.navigate("home") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                onSneakerClick = { sneakerId -> navController.navigate("details/$sneakerId") },
                                onDarkModeToggle = {
                                    val newDark = !isDark
                                    prefs.edit().putBoolean("dark_mode", newDark).apply()
                                    AppCompatDelegate.setDefaultNightMode(
                                        if (newDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                                    )
                                    recreate()
                                },
                                isDarkTheme = isDark
                            )
                        }

                        composable(
                            route = "details/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                            val sneaker = SneakerData.list.find { it.id == id }
                            if (sneaker != null) {
                                LaunchedEffect(sneaker.id) {
                                    RecentlyViewedManager.add(sneaker)
                                }
                                DetailsScreen(sneaker = sneaker, onBack = { navController.popBackStack() })
                            } else {
                                LaunchedEffect(Unit) { navController.popBackStack() }
                            }
                        }

                        composable("cart") {
                            CartScreen(onCheckout = { navController.navigate("checkout") })
                        }

                        composable("checkout") {
                            CheckoutScreen(
                                onBackToHome = {
                                    navController.navigate("home") {
                                        popUpTo("home") { inclusive = true }
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
}
