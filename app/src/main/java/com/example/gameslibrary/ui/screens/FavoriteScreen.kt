package com.example.gameslibrary.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.ui.components.GameItem
import com.example.gameslibrary.ui.components.ReviewItem
import com.example.gameslibrary.viewmodel.AuthViewModel
import com.example.gameslibrary.viewmodel.TopBarViewModel

@Composable
fun FavoriteScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    topBarViewModel: TopBarViewModel = hiltViewModel()
) {
    val tabItems = listOf("Поиграю", "Играл", "Отзывы")
    val selectedTab by topBarViewModel.tab.collectAsState()

    val wishlistGames by authViewModel.wishlist.collectAsState()
    val playedGames by authViewModel.reviews.collectAsState()
    val reviewedGames by authViewModel.reviews1.collectAsState()

    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            topBarViewModel.setSelectedTab(pagerState.currentPage)
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { tabIndex ->
        when (tabIndex) {
            0 -> GameList(games = wishlistGames, navController = navController, isPlayed = true)
            1 -> GameList(games = playedGames, navController = navController, isPlayed = false)
            2 -> ReviewList(reviews = reviewedGames, navController = navController, topBarViewModel)
        }
    }
}

@Composable
fun GameList(games: List<GameModel>, navController: NavController, isPlayed: Boolean) {

    if (games.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Здесь пока пусто 😕",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
    }
    else {
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            itemsIndexed(games) { index, game ->
                GameItem(game, navController, isPlayed)
            }
        }
    }
}

@Composable
fun ReviewList(reviews: List<ReviewModel>, navController: NavController, topBarViewModel: TopBarViewModel) {
    if (reviews.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Здесь пока пусто 😕",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            itemsIndexed(reviews) { _, review ->
                ReviewItem(review, navController, topBarViewModel, true)
            }
        }
    }
}