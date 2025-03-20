package com.example.gameslibrary.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gameslibrary.R
import com.example.gameslibrary.ui.components.MyTextField
import com.example.gameslibrary.ui.components.horizontalGradient
import com.example.gameslibrary.viewmodel.GameViewModel
import com.example.gameslibrary.viewmodel.TopBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNavigation(navController: NavController, topBarViewModel: TopBarViewModel = hiltViewModel(), gameViewModel: GameViewModel = hiltViewModel()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "search"

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val title = topBarViewModel.title.value
    val subtitle = topBarViewModel.subtitle.value
    val titleFontSize = if (subtitle.isEmpty()) 20.sp else 16.sp  // Увеличиваем шрифт, если нет подзаголовка

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "search" -> {
                topBarViewModel.title.value = "Поиск"
                topBarViewModel.subtitle.value = ""
            }
            "filter" -> {
                topBarViewModel.title.value = "Фильтр"
                topBarViewModel.subtitle.value = ""
            }
           /* "game" -> {
                topBarViewModel.title.value = "Название игры"
                topBarViewModel.subtitle.value = "Разработчик"
            }*/
            "profile" -> {
                topBarViewModel.title.value = "Профиль"
                topBarViewModel.subtitle.value = ""
            }
          /*  "favorite" -> {
                topBarViewModel.title.value = "Избранное"
                topBarViewModel.subtitle.value = ""
            }*/
           /* "game/{gameTitle}" -> {
                topBarViewModel.title.value = "Избранное"
                topBarViewModel.subtitle.value = ""
            }*/
            else -> {
                topBarViewModel.title.value = ""
                topBarViewModel.subtitle.value = ""
            }
        }
    }

    CenterAlignedTopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
        ),
        title = {
            if (currentRoute == "search") {
                var searchQuery by remember { mutableStateOf(gameViewModel.searchQuery.value) }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        gameViewModel.onSearchQueryChanged(it)
                    },
                    placeholder = {
                        Text(text = "Поиск...", color = Color.Gray, modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.Transparent,
                        unfocusedTextColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).background(
                        color = colorResource(R.color.black1),
                        shape = RoundedCornerShape(16.dp)
                    ),
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    searchQuery = ""
                                    gameViewModel.onSearchQueryChanged("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Очистить",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )
            } else if (currentRoute == "favorite") {
                val tabItems = listOf("Поиграю", "Играл", "Отзывы")
                val selectedTab by topBarViewModel.tab.collectAsState()
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
                                topBarViewModel.setSelectedTab(index)
                            },
                            text = {
                                Text(text, fontSize = 14.sp)
                            }
                        )
                    }
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = titleFontSize,
                        fontWeight = FontWeight.Bold,
                        lineHeight = titleFontSize,
                    )
                    if (subtitle.isNotEmpty()) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            subtitle,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.whiteText)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (currentRoute in listOf("filter", "profile1", "game/{gameTitle}")) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        contentDescription = "Localized description",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(6.dp))
                            .padding(2.dp)
                    )
                }
            }
        },
        actions = {
            if (currentRoute == "profile") {
                IconButton(onClick = { topBarViewModel.changeProfile() }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            } else if (currentRoute == "search") {
                IconButton(onClick = { navController.navigate("filter")}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_filter),
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )

    /*Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { *//* Действие назад *//* }) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_close), contentDescription = "Назад", tint = Color.White)
        }

        Text(
            text = "Заголовок",
            color = Color.White,
            fontSize = 18.sp,
        )

        IconButton(onClick = { *//* Действие настроек *//* }) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Настройки", tint = Color.White)
        }
    }*/
}

@Composable
fun CustomSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(
                color = Color.Gray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .border(1.dp, if (isFocused) Color.Blue else Color.Gray, RoundedCornerShape(8.dp))
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
    ) {
        // Text Field Placeholder
        if (value.isEmpty()) {
            Text(
                text = "Search...",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        // Actual Text Input
        Text(
            text = value,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 24.dp) // Добавим место для крестика
        )

        // Clear Button (Crossover)
        if (value.isNotEmpty()) {
            IconButton(
                onClick = { onValueChange("") },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.Gray
                )
            }
        }
    }
}