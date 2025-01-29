package com.example.gameslibrary

import android.view.Menu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource

@Composable
fun NavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Обзор", Icons.Filled.Search, Icons.Outlined.Search, "search"),
        BottomNavItem("Избранное", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "favorite"),
        BottomNavItem("Профиль", Icons.Filled.Person, Icons.Outlined.Person, "profile")
    )
    Box(
        modifier = Modifier.background(Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = 10f,
            endY = 130f
        ))
    ) {
        NavigationBar(
            containerColor = Color.Transparent
        ) {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            items.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    colors = NavigationBarItemColors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray,
                        selectedIndicatorColor = Color.Transparent,
                        disabledIconColor = Color.White,
                        disabledTextColor = Color.White
                    ),
                    icon = {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            tint = if (selected) Color.White else colorResource(R.color.red1)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (selected) Color.White else colorResource(R.color.red1)
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

