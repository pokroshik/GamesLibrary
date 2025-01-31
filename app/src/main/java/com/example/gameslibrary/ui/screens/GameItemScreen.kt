package com.example.gameslibrary.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.GameModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameItemScreen() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val gradientBrush = Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    )

    val game = GameModel(
        Developer = "CD Projekt Red",
        Genre = arrayListOf("RPG", "Action"),
        Metacritic = mutableMapOf("PC" to 86.0, "PS4" to 90.0),
        Title = "The Dark Pictures Anthology: Man of medan",
        about = "Cuphead – это в прямом смысле слова \"классический\" платформер. Классический, потому что все в нем выдержано в духе 1930-х: от графики, кажется, вышедшей из-под пера самого Уолта Диснея, до акварельных фонов и джазового музыкального сопровождения.",
        controller = true,
        disk = "Blu-ray",
        platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation", "Xbox", "Nintendo"),
        poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
        release = Timestamp.now(),
        require = mutableMapOf("RAM" to "16GB", "GPU" to "RTX 2060"),
        stores = mutableMapOf("Steam" to "https://store.steampowered.com/app/1091500"),
        time = mutableMapOf("Main Story" to 25.0, "Completionist" to 100.0)
    )

    val date = game.release?.toDate()

    val formattedDate = date?.let {
        val dateFormatter =
            SimpleDateFormat("d MMMM yyyy", Locale("ru")) //
        dateFormatter.format(it)
    } ?: "52"

    Box(
        Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.1f,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(280.dp, 360.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = game.Title,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = game.Developer,
                color = colorResource(R.color.whiteText),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(14.dp))
            Box (
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.searchButton),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = formattedDate,
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()// Отступы между иконками
            ) {
                Button(
                    contentPadding = PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.searchButton),
                        contentColor = Color.White
                    ),
                    onClick = { },
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Image(
                        painter = painterResource(id= R.drawable.circle),
                        contentDescription = null,
                        modifier = Modifier
                            .height(20.dp)
                            .drawWithContent {
                                drawContent()
                                drawCircle(
                                    brush = gradientBrush,
                                    radius = size.minDimension / 2,
                                    center = Offset(size.width / 2, size.height / 2),
                                    style = Stroke(width = size.minDimension / 2 * 0.02f)
                                )
                            }
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "2",
                        fontSize = 14.sp,
                    )
                }
                Button(
                    contentPadding = PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.searchButton),
                        contentColor = Color.White
                    ),
                    onClick = { },
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_colored_metacritic),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = game.Metacritic["PC"].toString(),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Button(
                    contentPadding = PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.searchButton),
                        contentColor = Color.White
                    ),
                    onClick = { },
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_userscore),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = game.Metacritic["PS4"].toString(),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
                        )
                    )
            )
            Spacer(Modifier.height(6.dp))
            Row (
                horizontalArrangement =  Arrangement.spacedBy(20.dp)
            ){
                Button(
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.searchButton),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_outlined_bookmark),
                        contentDescription = null,
                        modifier =  Modifier
                            .size(24.dp)
                            .graphicsLayer(alpha = 0.99f) // Убираем артефакты
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = gradientBrush,
                                        blendMode = BlendMode.SrcAtop
                                    )
                                }
                            })

                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Поиграю",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
                Button(
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.searchButton),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Star,
                        contentDescription = null,

                        )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Играл",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Об игре",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                lineHeight = 18.sp,
                text = game.about,
                color = colorResource(R.color.whiteText),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
   /* val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // Разрешаем промежуточное состояние
    )

    ModalBottomSheet(
        onDismissRequest = {}, // Отключаем закрытие по тапу вне
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize() // Позволяем растягиваться на весь экран
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp) // Минимальная высота (не опускается ниже)
                .padding(16.dp)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Это информация об игре.", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }*/
}