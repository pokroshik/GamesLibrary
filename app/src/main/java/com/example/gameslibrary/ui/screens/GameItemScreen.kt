package com.example.gameslibrary.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gameslibrary.R
import com.example.gameslibrary.ui.components.CrossedIcon
import com.example.gameslibrary.ui.components.GradientButton
import com.example.gameslibrary.ui.components.GradientIcon
import com.example.gameslibrary.ui.components.GradientIconVector
import com.example.gameslibrary.ui.components.MyDivider
import com.example.gameslibrary.ui.components.MyIcon
import com.example.gameslibrary.ui.components.ReviewItem
import com.example.gameslibrary.ui.components.SliderWithText
import com.example.gameslibrary.ui.components.TextButton
import com.example.gameslibrary.ui.components.horizontalGradient
import com.example.gameslibrary.viewmodel.AuthViewModel
import com.example.gameslibrary.viewmodel.GameViewModel
import com.example.gameslibrary.viewmodel.TopBarViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GameItemScreen(pv: PaddingValues, title: String, navController: NavController, viewModel: GameViewModel = hiltViewModel(), authModel: AuthViewModel = hiltViewModel(), topBarViewModel: TopBarViewModel = hiltViewModel()) {
    val game1 by viewModel.game.collectAsState()
    val reviews1 by viewModel.reviews.collectAsState()
    val uid = authModel.getUserUid() ?: "no"
    val name = authModel.mainUser.value?.firstName ?: "no"
    var showSecondSheet by remember { mutableStateOf(false) }

    LaunchedEffect(title) {
        viewModel.getGameByTitle(title, uid)
        topBarViewModel.title.value = title
    }
    LaunchedEffect(game1) {
        game1?.let { game ->
            topBarViewModel.subtitle.value = game.Developer
        }
    }
    if (game1 == null) {
        CircularProgressIndicator()
    } else {
        val game = game1
        game?.let {
            val averageRating = reviews1
                .mapNotNull { it.rating }
                .takeIf { it.isNotEmpty() }
                ?.average()
                ?: "TBD"

            val reviews = listOf(
                averageRating.toString() to R.drawable.circle,
                game.Metacritic.get("metascore").toString() to R.drawable.ic_colored_metacritic,
                game.Metacritic.get("user score").toString() to R.drawable.ic_userscore,
            )
            val tabItems = listOf("Минимальные", "Рекомендуемые")

            var hour = game.time["main"] ?: 1.0
            var timeS = ""
            var timeS1 = ""

            if (hour % 1.0 == 0.0) {
                timeS = hour.toInt().toString()
            } else {
                timeS = hour.toString()
            }
            hour = game.time["full"] ?: 1.0
            if (hour % 1.0 == 0.0) {
                timeS1 = hour.toInt().toString()
            } else {
                timeS1 = hour.toString()
            }
            val time = listOf(
                timeS to R.drawable.ic_story,
                timeS1 to R.drawable.ic_trophy,
            )

            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val gradientBrush = Brush.horizontalGradient(
                listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
            )
            val platformIcons = mapOf(
                "Mac" to R.drawable.ic_platform_apple,
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
            } ?: "Неизвестно"

            Box(
                Modifier.fillMaxSize().padding()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                ) {
                    AsyncImage(
                        model = game.poster,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.1f,
                        modifier = Modifier.fillMaxSize().blur(10.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = game.poster,
                            contentDescription = null,
                            modifier = Modifier.width(screenWidth * 0.65f)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = false,
            )

            ModalBottomSheet(
                containerColor = colorResource(R.color.searchBackground),
                onDismissRequest = {
                    navController.popBackStack()
                },
                sheetState = sheetState,
                scrimColor = Color.Transparent,
                dragHandle = {},
                modifier = Modifier.fillMaxSize().padding(top = pv.calculateTopPadding() / 4)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
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
                    if (game.disk != null) {
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
                                                        center = Offset(
                                                            size.width / 2,
                                                            size.height / 2
                                                        ),
                                                        style = Stroke(width = size.minDimension / 2 * 0.02f)
                                                    )
                                                }
                                        )
                                    } else {
                                        MyIcon(
                                            22.dp,
                                            image,
                                            if (index == 1) Color.Unspecified else Color.White
                                        )
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
                    }
                    Spacer(Modifier.height(8.dp))
                    MyDivider()
                    Spacer(Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (!game.isPlayed) {
                            Button(
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.searchButton),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.changeGameWishlist(game.documentId, uid) }
                            ) {

                                if (game.isWish) GradientIcon(
                                    24.dp,
                                    R.drawable.ic_filled_bookmark
                                ) else MyIcon(24.dp, R.drawable.ic_outlined_bookmark)

                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "Поиграю",
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }
                        }
                        if (game.disk != null) {
                            Button(
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.searchButton),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f),
                                onClick = { showSecondSheet = true }
                            ) {
                                if (game.isPlayed)
                                    GradientIconVector(24.dp, Icons.TwoTone.Star)
                                else {
                                    Icon(
                                        imageVector = Icons.TwoTone.Star,
                                        contentDescription = null,
                                    )
                                }
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "Играл",
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(game.images) { index, image ->
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
                                AsyncImage(
                                    model = image,
                                    contentDescription = null,
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
                    if (game.disk != null) {
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
                                text = game.disk ?: "hi",
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
                            if (game.controller ?: false) MyIcon(
                                24.dp,
                                R.drawable.ic_controller
                            ) else CrossedIcon(2.dp, 24.dp, R.drawable.ic_controller)
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
                        val context = LocalContext.current

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
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(game.stores.get(shop)))
                                    context.startActivity(intent)
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start, // Выравнивание по левой стороне
                                    verticalAlignment = Alignment.CenterVertically // Центрирование по вертикали
                                ) {
                                    MyIcon(
                                        26.dp,
                                        shopsIcon[shop] ?: R.drawable.ic_close,
                                        Color.Unspecified
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(shop)
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Отзывы",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    if (reviews1.isEmpty()) {
                        Box(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "Отзывов пока нет",
                                color = colorResource(R.color.whiteText),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    reviews1.forEach { review ->
                        ReviewItem(review, navController, topBarViewModel)
                    }
                    Spacer(Modifier.height(40.dp))
                }
            }

            //Review
            var selectedRating by remember { mutableStateOf(5f) }

            if (showSecondSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSecondSheet = false },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = colorResource(R.color.searchBackground)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Добавить отзыв",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        SliderWithText(
                            value = selectedRating,
                            valueRange = 1f..10f,
                            onValueChange = { newValue ->
                                selectedRating = newValue
                            }
                        )
                        var review by remember { mutableStateOf("sd") }
                        TextField(
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = colorResource(R.color.searchButton),
                                focusedLabelColor = Color.Red.copy(alpha = 0.5f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Color.Red.copy(alpha = 0.5f),
                                cursorColor = Color.Red.copy(alpha = 0.5f),
                            ),
                            value = review,
                            onValueChange = { review = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp, max = 250.dp)
                                .verticalScroll(rememberScrollState()),
                            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                            maxLines = 10,
                            label = { Text("Ваш отзыв (более 10 символов)") },
                            shape = RoundedCornerShape(16.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        GradientButton(
                            "Отправить",
                            enabled = review.length > 10 || review.length == 0,
                            {
                                viewModel.changeReview(
                                    game.documentId,
                                    uid,
                                    name,
                                    review,
                                    selectedRating.toInt()
                                )
                                review = ""
                                viewModel.loadReview(game.documentId)
                                showSecondSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}


