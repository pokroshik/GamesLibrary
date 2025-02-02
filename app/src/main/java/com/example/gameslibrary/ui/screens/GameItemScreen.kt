package com.example.gameslibrary.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.ui.components.CrossedIcon
import com.example.gameslibrary.ui.components.GradientIcon
import com.example.gameslibrary.ui.components.MyDivider
import com.example.gameslibrary.ui.components.MyIcon
import com.example.gameslibrary.ui.components.TextButton
import com.example.gameslibrary.ui.components.horizontalGradient
import com.example.gameslibrary.ui.navigation.TopAppNavigation
import com.google.android.material.tabs.TabItem
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GameItemScreen(pv: PaddingValues) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val game = GameModel(
        Developer = "CD Projekt Red",
        Genre = arrayListOf("RPG", "Action"),
        Metacritic = mutableMapOf("PC" to 86.0, "PS4" to 90.0),
        Title = "The Dark Pictures Anthology: Man of medan",
        about = "Cuphead – это в прямом смысле слова \"классический\" платформер. Классический, потому что все в нем выдержано в духе 1930-х: от графики, кажется, вышедшей из-под пера самого Уолта Диснея, до акварельных фонов и джазового музыкального сопровождения.",
        controller = true,
        disk = "64 GB",
        platforms = arrayListOf("Windows", "Apple", "Linux", "Playstation", "Xbox", "Nintendo"),
        poster = "https://res.cloudinary.com/dclbo73wm/image/upload/v1738093408/poster_ghzafj.jpg",
        release = Timestamp.now(),
        require = mutableMapOf(
            "min" to mapOf(
                "Видеокарта" to "Nvidia GTX 460",
                "ОС" to "64-битная Windows 7",
                "Оперативная память" to "4 ГБ",
                "Процессор" to "Intel CPU Core i3"
            ),
            "recommend" to mapOf(
                "Видеокарта" to "Nvidia GPU GeForce GTX 660",
                "ОС" to "64-битная Windows 7",
                "Оперативная память" to "8 ГБ",
                "Процессор" to "Intel CPU Core i7"
            )
        ),
        stores = mutableMapOf(
            "Steam" to "https://store.steampowered.com/app/424840/Little_Nightmares",
            "Epic Games" to
                    "https://store.epicgames.com/ru/p/little-nightmares",
            "PlayStation Store" to "https://store.playstation.com/en-us/product/UP9000-PPSA21564_00-0000000000000000"
        ),
        time = mutableMapOf("Main Story" to 25.2, "Completionist" to 100.0)
    )

    val reviews = listOf(
        "2" to R.drawable.circle,
        game.Metacritic["PC"].toString() to R.drawable.ic_colored_metacritic,
        game.Metacritic["PS4"].toString() to R.drawable.ic_userscore,
    )
    val tabItems = listOf("Минимальные", "Рекомендуемые")

    var hour = game.time["Main Story"] ?: 1.0
    var timeS = ""
    var timeS1 = ""

    if (hour % 1.0 == 0.0) {
        timeS = hour.toInt().toString()
    } else {
        // Иначе выводим с десятичной частью
        timeS = hour.toString()
    }
    hour = game.time["Completionist"] ?: 1.0
    if (hour % 1.0 == 0.0) {
        timeS1 = hour.toInt().toString()
    } else {
        // Иначе выводим с десятичной частью
        timeS1 = hour.toString()
    }
    val time = listOf(
        timeS to R.drawable.ic_story,
        timeS1 to R.drawable.ic_trophy,
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val gradientBrush = Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    )
    val platformIcons = mapOf(
        "Apple" to R.drawable.ic_platform_apple,
        "Linux" to R.drawable.ic_platform_linux,
        "Nintendo" to R.drawable.ic_platform_nintendo,
        "Playstation" to R.drawable.ic_platform_playstation,
        "Windows" to R.drawable.ic_platform_windows,
        "Xbox" to R.drawable.ic_platform_xbox,
    )

    val date = game.release?.toDate()

    val formattedDate = date?.let {
        val dateFormatter =
            SimpleDateFormat("d MMMM yyyy", Locale("ru")) //
        dateFormatter.format(it)
    } ?: "52"


    Box(
        Modifier.fillMaxSize().padding()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
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
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.poster),
                    contentDescription = null,
                    modifier = Modifier
                        .width(screenWidth * 0.65f)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
     val sheetState = rememberModalBottomSheetState(
      skipPartiallyExpanded = false, // Разрешаем промежуточное состояние
  )

    ModalBottomSheet(
      containerColor = colorResource(R.color.searchBackground),
      onDismissRequest = {}, // Отключаем закрытие по тапу вне
      sheetState = sheetState,
      scrimColor = Color.Transparent,
        dragHandle = {},
      modifier = Modifier.fillMaxSize().padding(top = pv.calculateTopPadding()/4) // Позволяем растягиваться на весь экран
  ) {
      Column(
          verticalArrangement = Arrangement.spacedBy(2.dp),
          modifier = Modifier.verticalScroll(rememberScrollState())
              //  .align(Alignment.BottomCenter)
              .padding(horizontal = 20.dp).fillMaxSize()
      ) {
          Spacer(Modifier.height(15.dp))
          Text(
              text = game.Title,
              color = Color.White,
              fontSize = 26.sp,
              fontWeight = FontWeight.Bold
          )
          Text(
              text = game.Developer,
              color = colorResource(R.color.whiteText),
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
          )
          Spacer(Modifier.height(8.dp))
          TextButton(vPadding = 8.dp) {
              Text(
                  text = formattedDate,
                  color = Color.White,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
              )
          }
          Spacer(Modifier.height(2.dp))
          Row(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.fillMaxWidth()
          ) {

              repeat(reviews.size) { index ->
                  val (text, image) = reviews[index]

                  TextButton(vPadding = 6.dp) {
                      if (index == 0) {
                          Image(
                              painter = painterResource(id = image),
                              contentDescription = null,
                              modifier = Modifier
                                  .height(24.dp)
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
                      } else {
                          MyIcon(22.dp, image, if (index == 1) Color.Unspecified else Color.White)
                      }
                      Text(
                          text = text,
                          fontSize = 14.sp,
                          color = Color.White,
                          fontWeight = FontWeight.Bold
                      )
                  }
              }
          }
          Spacer(Modifier.height(8.dp))
          MyDivider()
          Spacer(Modifier.height(6.dp))
          Row(
              horizontalArrangement = Arrangement.spacedBy(20.dp)
          ) {
              Button(
                  shape = RoundedCornerShape(12.dp),
                  colors = ButtonDefaults.buttonColors(
                      containerColor = colorResource(R.color.searchButton),
                      contentColor = Color.White
                  ),
                  modifier = Modifier.weight(1f),
                  onClick = {}
              ) {
                  GradientIcon(24.dp, R.drawable.ic_filled_bookmark)

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
          Spacer(Modifier.height(6.dp))
          Text(
              lineHeight = 18.sp,
              text = game.about,
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )

          Spacer(Modifier.height(25.dp))

          LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp), // Отступы между изображениями
              modifier = Modifier.fillMaxWidth() // Отступы и растягивание на всю ширину
          ) {
              items(5) { index ->
                  Card(
                      colors = CardDefaults.cardColors(
                          containerColor = colorResource(R.color.searchBackground)
                      ),
                      modifier = Modifier
                          .width(screenWidth * 0.85f)
                          .height(screenWidth * 0.85f * 9 / 16),
                      shape = MaterialTheme.shapes.medium,
                      elevation = CardDefaults.cardElevation(8.dp)
                  ) {
                      // Вставка изображения
                      Image(
                          painter = painterResource(id = R.drawable.stray),
                          contentDescription = "Image $index",
                          modifier = Modifier.fillMaxSize()
                      )
                  }
              }
          }


          Spacer(Modifier.height(10.dp))
          Text(
              lineHeight = 18.sp,
              text = "Жанры",
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )
          FlowRow(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
              game.Genre.forEach { item ->
                  TextButton(shapeDp = 10.dp, vPadding = 8.dp, hPadding = 10.dp) {
                      Text(
                          text = item,
                          fontSize = 14.sp,
                          color = Color.White,
                          fontWeight = FontWeight.Bold
                      )
                  }
              }
          }
          Spacer(Modifier.height(4.dp))
          Text(
              lineHeight = 18.sp,
              text = "Платформы",
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )
          FlowRow(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
              game.platforms.forEach { item ->
                  TextButton(shapeDp = 10.dp, vPadding = 8.dp) {
                      platformIcons[item]?.let { iconRes ->
                          MyIcon(16.dp, iconRes)
                      }
                      Text(
                          text = item,
                          fontSize = 14.sp,
                          color = Color.White,
                          fontWeight = FontWeight.Bold
                      )
                  }
              }
          }
          Spacer(Modifier.height(4.dp))
          Text(
              lineHeight = 18.sp,
              text = "Время для прохождения",
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )
          FlowRow(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
              repeat(time.size) { index ->
                  val (text, image) = time[index]
                  TextButton(shapeDp = 10.dp, vPadding = 8.dp) {
                      Icon(
                          painter = painterResource(id = image),
                          contentDescription = null,
                          modifier = Modifier.size(20.dp),
                          tint = Color.White
                      )
                      Text(
                          text = text + " ч.",
                          fontSize = 14.sp,
                          color = Color.White,
                          fontWeight = FontWeight.Bold
                      )
                  }
              }
          }

          Spacer(Modifier.height(4.dp))
          Text(
              lineHeight = 18.sp,
              text = "Размер игры",
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )
          TextButton(10.dp, vPadding = 8.dp, shapeDp = 10.dp) {
              GradientIcon(20.dp, R.drawable.ic_disk)
              Text(
                  text = game.disk,
                  fontSize = 14.sp,
                  color = Color.White,
                  fontWeight = FontWeight.Bold
              )

          }

          Spacer(Modifier.height(4.dp))
          Text(
              text = "Поддержка контролера",
              color = colorResource(R.color.whiteText),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
          )
          TextButton(vPadding = 6.dp, hPadding = 10.dp, shapeDp = 10.dp) {
              CrossedIcon(2.dp, 24.dp, R.drawable.ic_controller)
          }

          Spacer(Modifier.height(15.dp))
          Text(
              text = "Системные требования",
              color = Color.White,
              fontSize = 26.sp,
              fontWeight = FontWeight.Bold
          )

          var selectedTab by remember { mutableIntStateOf(0) }
          val pagerState = rememberPagerState {
              tabItems.size
          }
          LaunchedEffect(selectedTab) {
              pagerState.animateScrollToPage(selectedTab)
          }
          LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
              if (!pagerState.isScrollInProgress) {
                  selectedTab = pagerState.currentPage
              }
          }

          val tabName = listOf("min", "recommend")
          val specs = listOf("ОС", "Процессор", "Оперативная память", "Видеокарта")
          val shopsIcon = mapOf(
              "Steam" to R.drawable.ic_steam,
              "Epic Games" to R.drawable.ic_epic_games,
              "PlayStation Store" to R.drawable.ic_ps_store
          )
          Card(
              colors = CardDefaults.cardColors(
                  containerColor = colorResource(R.color.searchBackground)
              ),
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(16.dp),
              elevation = CardDefaults.cardElevation(8.dp)
          ) {
              TabRow(
                  selectedTabIndex = selectedTab,
                  containerColor = Color.Transparent,
                  indicator = { tabPositions ->
                      Box(
                          modifier = Modifier
                              .tabIndicatorOffset(tabPositions[selectedTab])
                              .height(3.dp)
                              .padding(horizontal = 34.dp)
                              .background(
                                  horizontalGradient(),
                                  shape = RoundedCornerShape(
                                      bottomStart = 16.dp,
                                      bottomEnd = 16.dp
                                  )
                              )
                      )
                  },
                  divider = { },
              ) {
                  tabItems.forEachIndexed { index, text ->
                      Tab(
                          selectedContentColor = Color.White,
                          unselectedContentColor = colorResource(R.color.whiteText),
                          selected = selectedTab == index,
                          onClick = {
                              selectedTab = index
                          },
                          text = {
                              Text(text, fontSize = 14.sp)
                          }
                      )
                  }
              }
              HorizontalPager(
                  state = pagerState,
                  modifier = Modifier.fillMaxWidth().padding(8.dp)
              ) { tabIndex ->
                  Column(
                      verticalArrangement = Arrangement.spacedBy(2.dp)
                  ) {
                      repeat(specs.size) { index ->
                          val spec = specs[index]
                          Text(
                              text = spec + ":",
                              color = colorResource(R.color.whiteText),

                              )
                          Text(
                              text = (game.require[tabName[tabIndex]] as? Map<String, String>)?.get(
                                  spec
                              ) ?: "Не найдено",
                              color = Color.White,
                              fontWeight = FontWeight.Bold
                          )
                          Spacer(Modifier.height(4.dp))
                      }
                  }
              }
          }
          Spacer(Modifier.height(10.dp))
          Text(
              text = "Магазины",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
          )
          game.stores.keys.forEach { shop ->
              Button(
                  colors = ButtonDefaults.buttonColors(
                      containerColor = colorResource(R.color.searchButton)
                  ),
                  modifier = Modifier.fillMaxWidth(),
                  contentPadding = PaddingValues(horizontal = 10.dp),
                  shape = RoundedCornerShape(10.dp),
                  onClick = {
                      /*val url = gameShops[shop]
                    url?.let { openUrl(it) } // openUrl — это псевдокод для открытия ссылки*/
                  }) {
                  Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.Start, // Выравнивание по левой стороне
                      verticalAlignment = Alignment.CenterVertically // Центрирование по вертикали
                  ) {
                      MyIcon(26.dp, shopsIcon[shop] ?: R.drawable.ic_close, Color.Unspecified)
                      Spacer(Modifier.width(6.dp))
                      Text(shop)
                  }
              }
          }
          Spacer(Modifier.height(40.dp))
      }

  }
}

//}