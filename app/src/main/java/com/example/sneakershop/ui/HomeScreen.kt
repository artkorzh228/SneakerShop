package com.example.sneakershop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sneakershop.R
import com.example.sneakershop.model.RecentlyViewedManager
import com.example.sneakershop.model.Sneaker
import com.example.sneakershop.viewmodel.HomeViewModel
import com.example.sneakershop.viewmodel.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSneakerClick: (String) -> Unit,
    onDarkModeToggle: () -> Unit,
    isDarkTheme: Boolean,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSortMenu by remember { mutableStateOf(false) }
    val recentlyViewed = RecentlyViewedManager.items

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            IconButton(onClick = onDarkModeToggle) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = if (isDarkTheme) "Светлая тема" else "Тёмная тема"
                )
            }
        }

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(uiState.categories, key = { it }) { category ->
                val isSelected = category == uiState.selectedCategory
                Button(
                    onClick = { viewModel.onCategorySelected(category) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text(category)
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price: ${"%.0f".format(uiState.priceMin)} $ — ${"%.0f".format(uiState.priceMax)} $",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Сортировка")
                        }
                        Text(
                            text = when (uiState.sortOrder) {
                                SortOrder.DEFAULT -> "Default"
                                SortOrder.PRICE_ASC -> "Low→High"
                                SortOrder.PRICE_DESC -> "High→Low"
                            },
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Default") },
                            onClick = {
                                viewModel.onSortOrderChanged(SortOrder.DEFAULT)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Price: Low to High") },
                            onClick = {
                                viewModel.onSortOrderChanged(SortOrder.PRICE_ASC)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Price: High to Low") },
                            onClick = {
                                viewModel.onSortOrderChanged(SortOrder.PRICE_DESC)
                                showSortMenu = false
                            }
                        )
                    }
                }
            }
            RangeSlider(
                value = uiState.priceMin..uiState.priceMax,
                onValueChange = { range ->
                    viewModel.onPriceRangeChanged(range.start, range.endInclusive)
                },
                valueRange = 0f..300f,
                steps = 29,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (recentlyViewed.isNotEmpty()) {
            Text(
                text = "Recently Viewed",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(recentlyViewed, key = { it.id }) { sneaker ->
                    RecentlyViewedCard(sneaker = sneaker, onClick = { onSneakerClick(sneaker.id) })
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiState.sneakers, key = { it.id }) { sneaker ->
                SneakerItem(
                    sneaker = sneaker,
                    onClick = { onSneakerClick(sneaker.id) }
                )
            }
        }
    }
}

@Composable
private fun RecentlyViewedCard(sneaker: Sneaker, onClick: () -> Unit) {
    val context = LocalContext.current
    val resId = remember(sneaker.imageUrl) {
        context.resources.getIdentifier(sneaker.imageUrl, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = if (resId != 0) painterResource(id = resId) else painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = sneaker.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = sneaker.name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
                Text(
                    text = "${sneaker.price}$",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
