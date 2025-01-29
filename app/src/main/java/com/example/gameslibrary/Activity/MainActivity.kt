package com.example.gameslibrary.Activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gameslibrary.Domain.GameModel
import com.example.gameslibrary.GameItem
import com.example.gameslibrary.NavigationBar
import com.example.gameslibrary.R
import com.example.gameslibrary.ViewModel.MainViewModel
import com.google.firebase.Timestamp

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

@Composable
fun MainScreen(navController: NavHostController, onItemClick: (GameModel) ->Unit = {}) {
    Scaffold(
        content = {paddingValues ->
            Box(
                modifier = Modifier.background(
                Brush.radialGradient(
                    colors = listOf(Color.Black, colorResource(R.color.red)),
                    radius = 4000f
                )
            ).padding(
                top = paddingValues.calculateTopPadding(),
                /*start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                bottom = 0.dp*/
            ).background(Color.Transparent).fillMaxSize()) {
                NavHost(navController, startDestination = "search") {
                    composable("favorite") { FavoriteScreen() }
                    composable("search") { SearchScreen(onItemClick) }
                    composable("profile") { ProfileScreen() }
                }
            }
        },
        bottomBar = { NavigationBar(navController) }
    )
}

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Favorite Screen", fontSize = 24.sp)
    }
}

@Composable
fun SearchScreen(onItemClick: (GameModel) -> Unit) {
    val viewModel = MainViewModel()
    val games = remember { mutableStateListOf<GameModel>() }
    var showGames by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadUpcoming().observeForever {
            games.clear()
            games.addAll(it)
            showGames = false
        }
    }

 /*   val games = remember { getGames() } // Загрузка списка игр
    SectionTitle("New movies")
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 80.dp)
        ) {
            items(games) { game ->
                GameItem(game, onItemClick)
            }
        }*/

    if (showGames) {
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 80.dp)
        ) {
            items(games) { item ->
                GameItem(item, onItemClick)
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 24.sp)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            color = colorResource(R.color.whiteText),
            fontSize = 18.sp
        )
    )
}

fun getGames(): List<GameModel> {
    return listOf(
        GameModel(
            Developer = "CD Projekt Red",
            Genre = arrayListOf("RPG", "Action"),
            Metacritic = mutableMapOf("PC" to 86.0, "PS5" to 90.0),
            Title = "Cyberpunk 2077",
            about = "Футуристический открытый мир с RPG-элементами.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("PC", "PS5", "Xbox Series X"),
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
            platforms = arrayListOf("PC", "PS4", "Xbox One"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ),GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("PC", "PS4", "Xbox One"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ),GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("PC", "PS4", "Xbox One"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ),GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("PC", "PS4", "Xbox One"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "12GB", "GPU" to "GTX 1060"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1174180"),
            time = mutableMapOf("Main Story" to 50.0, "Completionist" to 150.0)
        ),GameModel(
            Developer = "Rockstar Games",
            Genre = arrayListOf("Action", "Adventure"),
            Metacritic = mutableMapOf("PC" to 93.0, "PS4" to 96.0),
            Title = "Red Dead Redemption 2",
            about = "Эпический вестерн с открытым миром.",
            controller = true,
            disk = "Blu-ray",
            platforms = arrayListOf("PC", "PS4", "Xbox One"),
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
            platforms = arrayListOf("PC", "PS4", "Xbox One", "Switch"),
            poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
            release = Timestamp.now(),
            require = mutableMapOf("RAM" to "8GB", "GPU" to "GTX 970"),
            stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/782330"),
            time = mutableMapOf("Main Story" to 15.0, "Completionist" to 30.0)
        )
    )
}


