package com.example.gameslibrary.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.overscroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gameslibrary.getGames
import com.example.gameslibrary.ui.components.GameItem
import com.example.gameslibrary.viewmodel.GameViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: GameViewModel = hiltViewModel()) {
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

    val games by viewModel.games.collectAsState()
    if (games.isEmpty()) {
        CircularProgressIndicator()
        viewModel.loadNextPage()
    }
    else {
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(games) { index, game ->
                GameItem(game, navController)
                if (index == games.lastIndex) {
                    viewModel.loadNextPage()
                }
            }
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