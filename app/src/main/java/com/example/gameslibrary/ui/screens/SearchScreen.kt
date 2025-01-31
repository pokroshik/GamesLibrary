package com.example.gameslibrary.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gameslibrary.getGames
import com.example.gameslibrary.ui.components.GameItem

@Composable
fun SearchScreen(navController: NavController) {
    /*    val viewModel = MainViewModel()
        val games = remember { mutableStateListOf<GameModel>() }
        var showGames by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            viewModel.loadUpcoming().observeForever {
                games.clear()
                games.addAll(it)
                showGames = false
            }
        }*/

    val games = remember { getGames() } // Загрузка списка игр
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        items(games) { game ->
            GameItem(game, navController)
        }
    }
    /*
    if (showGames) {d
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            items(games) { item ->
                GameItem(item, onItemClick)
            }
        }
    }*/
}