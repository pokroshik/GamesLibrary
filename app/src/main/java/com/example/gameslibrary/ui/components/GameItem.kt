package com.example.gameslibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.getGames
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GameItem(game: GameModel, navController: NavController) {

    val platformIcons = mapOf(
        "Apple" to R.drawable.ic_platform_apple,
        "Linux" to R.drawable.ic_platform_linux,
        "Nintendo" to R.drawable.ic_platform_nintendo,
        "Playstation" to R.drawable.ic_platform_playstation,
        "Windows" to R.drawable.ic_platform_windows,
        "Xbox" to R.drawable.ic_platform_xbox,
    )

    Column (
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)){
        Row (
        ){
/*            AsyncImage(
    model = game.poster,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.size(width = 120.dp, height = 180.dp).clip(RoundedCornerShape(16.dp))
)*/
            Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 150.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Spacer(modifier = Modifier)
                Text(
                    game.Title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )

                // Преобразуем Timestamp в Date
                val date = game.release?.toDate()

                val formattedDate = date?.let {
                    val dateFormatter =
                        SimpleDateFormat("d MMMM yyyy", Locale("ru")) //
                    dateFormatter.format(it)
                } ?: "52"

                Text(
                    text = formattedDate,
                    color = colorResource(R.color.whiteText),
                    fontSize = 12.sp,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Отступы между иконками
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_colored_metacritic),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = game.Metacritic["PC"].toString(),
                        fontSize = 12.sp,
                        color = colorResource(R.color.whiteText)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_userscore),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = colorResource(R.color.whiteText)
                    )
                    Text(
                        text = game.Metacritic["PS4"].toString(),
                        fontSize = 12.sp,
                        color = colorResource(R.color.whiteText)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Отступы между иконками
                ) {
                    game.platforms.forEach { platform ->
                        platformIcons[platform]?.let { iconRes ->
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = colorResource(R.color.whiteText)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(33.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.height(20.dp)
                ) {
                    game.Genre.forEach { item ->
                        Button(
                            contentPadding = PaddingValues(
                                horizontal = 10.dp,
                                vertical = 0.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.searchButton),
                                contentColor = Color.White
                            ),
                            onClick = { },
                            modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                        ) {
                            Text(
                                text = item,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.height(1.dp).fillMaxWidth().background(
                Brush.horizontalGradient(
                    listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
                )
            )
        )
    }
}