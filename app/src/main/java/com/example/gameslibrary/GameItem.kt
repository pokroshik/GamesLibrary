package com.example.gameslibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gameslibrary.Domain.GameModel
import com.google.type.Date
import java.text.SimpleDateFormat
import com.google.firebase.Timestamp
import java.time.Instant
import java.util.Locale

@Composable
fun GameItem(game:GameModel, onItemClick: (GameModel)->Unit) {
    Box(
        modifier = Modifier
        .fillMaxWidth().height(144.dp))  {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = Color.Gray)) {
            AsyncImage(
    model = game.poster,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.size(width = 120.dp, height = 180.dp).clip(RoundedCornerShape(16.dp))
)
         /*   Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )*/
            Spacer(modifier = Modifier.width(10.dp))
            Column(
            ) {
                Text(
                    game.Title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Преобразуем Timestamp в Date
                val date = game.release?.toDate()

                // Создаем форматтер для нужного формата даты
                val dateS = SimpleDateFormat("dd MMMM yyyy", Locale("ru")) // "ru" для русской локализации


                Text(text = dateS.toString(), color = colorResource(R.color.whiteText), fontSize = 12.sp)
                // Spacer(modifier = Modifier.height(59.dp))
                // сделать в несколько рядов для маленьких экранов
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    game.Genre.forEach { item ->
                        Button(
                            contentPadding = PaddingValues(
                                horizontal = 10.dp,
                                vertical = 0.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { },
                            modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp).padding(start = 4.dp)
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
    }
}