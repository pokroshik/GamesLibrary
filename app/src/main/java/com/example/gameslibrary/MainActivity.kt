package com.example.gameslibrary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gameslibrary.ui.screens.BaseActivity
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.ui.navigation.BottomAppNavigation
import com.example.gameslibrary.ui.navigation.TopAppNavigation
import com.example.gameslibrary.ui.screens.FilterScreen
import com.example.gameslibrary.ui.screens.GameItemScreen
import com.example.gameslibrary.ui.screens.SearchScreen
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Filter

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MainScreen(navController)
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController, onItemClick = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController, onItemClick: (GameModel) ->Unit = {}) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Scaffold(
            topBar = {
                TopAppNavigation(navController)
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier.background(Color.Black).padding(
                        top = paddingValues.calculateTopPadding()
                        /*start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                bottom = 0.dp*/
                    )
                        .background(Color.Transparent).fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                topStart = 26.dp,
                                topEnd = 26.dp
                            )
                        ).background(
                            colorResource(R.color.searchBackground)
                        )
                    ) {
                        NavHost(navController, startDestination = "filter") { // search
                            composable("filter") { FilterScreen()}
                            composable("favorite") { FavoriteScreen() }
                            composable("search") { SearchScreen(navController) }
                            composable("profile") { ProfileScreen() }
                            composable("game/{gameTitle}") { backStackEntry ->
                                val gameTitle = backStackEntry.arguments?.getString("gameTitle") ?: ""
                                GameItemScreen(paddingValues,gameTitle, navController )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                BottomAppNavigation(navController)
            }
        )
    }
}

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize().padding(), contentAlignment = Alignment.Center) {
        Text(text = "Favorite Screen", fontSize = 24.sp)
    }
}



@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 24.sp)
    }
}

fun getGames(): List<GameModel> {
    return listOf(
        GameModel(
            Developer = "CD Projekt Red",
            Genre = arrayListOf("RPG", "Action"),
            Metacritic = mutableMapOf("PC" to 86.0, "PS4" to 90.0),
            Title = "Cyberpunk 2077",
            about = "Футуристический открытый мир с RPG-элементами.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "16GB", "GPU" to "RTX 2060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1091500"),
            time = mutableMapOf("Main Story" to 25.0, "Completionist" to 100.0)
        ),
        GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ), GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ), GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ), GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ), GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ),
        GameModel(
            Developer = "id Software",
            Genre = arrayListOf("Shooter", "Action"),
            Metacritic = mutableMapOf("PC" to 88.0, "PS4" to 87.0),
            Title = "DOOM Eternal",
            about = "Динамичный шутер в аду.",
            controller = true,
            disk = "Digital",
            platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation","Xbox", "Nintendo"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "8GB", "GPU" to "GTX 970"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/782330"),
            time = mutableMapOf("Main Story" to 15.0, "Completionist" to 30.0)
        )
    )
}


