package com.example.gameslibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.getGames
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameItem(game: GameModel, navController: NavController) {

    val platformIcons = mapOf(
        "Mac" to R.drawable.ic_platform_apple,
        "Linux" to R.drawable.ic_platform_linux,
        "Nintendo" to R.drawable.ic_platform_nintendo,
        "Playstation" to R.drawable.ic_platform_playstation,
        "Windows" to R.drawable.ic_platform_windows,
        "Xbox" to R.drawable.ic_platform_xbox,
    )

    Card (
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.searchBackground)
        ),
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
            .clickable {
                navController.navigate("game/${game.Title}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.height(170.dp)
            ) {
                AsyncImage(
                    model = game.poster,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(width = 120.dp, height = 170.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                /* Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 160.dp)
                    .clip(RoundedCornerShape(16.dp))
            )*/
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Spacer(Modifier)
                    Text(
                        game.Title,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    // Преобразуем Timestamp в Date
                    val date = game.release?.toDate()

                    val formattedDate = date?.let {
                        val dateFormatter =
                            SimpleDateFormat("d MMMM yyyy", Locale("ru")) //
                        dateFormatter.format(it)
                    } ?: "Неизвестно"

                    Text(
                        text = formattedDate,
                        color = colorResource(R.color.whiteText),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (game.Metacritic["metascore"] != null) {
                        Spacer(Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp) // Отступы между иконками
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_colored_metacritic),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color.Unspecified
                            )
                            Text(
                                text = game.Metacritic["metascore"].toString(),
                                fontSize = 13.sp,
                                color = colorResource(R.color.whiteText),
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(Modifier.width(6.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_userscore),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = colorResource(R.color.whiteText)
                            )
                            Text(
                                text = game.Metacritic?.get("user score").toString(),
                                fontSize = 13.sp,
                                color = colorResource(R.color.whiteText),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(start = 2.dp)

                    ) {
                        game.platforms?.forEach { platform ->
                            platformIcons[platform]?.let { iconRes ->
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = colorResource(R.color.whiteText)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            game.Genre.forEach { item ->
                                TextButton {
                                    Text(
                                        text = item,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}