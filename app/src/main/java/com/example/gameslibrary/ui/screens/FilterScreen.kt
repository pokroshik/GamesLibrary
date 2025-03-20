package com.example.gameslibrary.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gameslibrary.R
import com.example.gameslibrary.ui.components.CustomRangeSlider
import com.example.gameslibrary.ui.components.GradientButton
import com.example.gameslibrary.ui.components.MyIcon
import com.example.gameslibrary.ui.components.SliderWithText
import com.example.gameslibrary.viewmodel.AuthViewModel
import com.example.gameslibrary.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameCount by viewModel.gameCount.collectAsState()
    var showGenres by remember { mutableStateOf(false) }
    val selGenres by viewModel.selectedGenres.collectAsState()
    var selectedGenres = selGenres
    val checkedBy by viewModel.controllerSupport.collectAsState()
    var checked = checkedBy

    val genres = listOf(
        "Атмосферная",
        "Будущее",
        "Головоломка",
        "Гонки",
        "Детектив",
        "Dungeons & Dragons",
        "Инди",
        "Интерактивное кино",
        "Карточная",
        "Казуальная",
        "Метроидвания",
        "Мифология",
        "MOBA",
        "Научная фантастика",
        "Несколько игроков",
        "Открытый мир",
        "Песочница",
        "Пиксельная",
        "Платформер",
        "Приключения",
        "Ритм-игра",
        "Ролевая",
        "Rogue-like",
        "Симулятор",
        "Слэшер",
        "Souls-like",
        "Стратегия",
        "Хоррор",
        "Экшн",
        "Выживание",
        "Глубокий сюжет",
        "Решения с последствиями",
        "Шутер"
    )

   /* var showPlatforms by remember { mutableStateOf(false) }
    var selectedPlatforms by remember { mutableStateOf(listOf<String>()) }
    val platforms = mapOf(
        "Mac" to R.drawable.ic_platform_apple,
        "Linux" to R.drawable.ic_platform_linux,
        "Nintendo" to R.drawable.ic_platform_nintendo,
        "Playstation" to R.drawable.ic_platform_playstation,
        "Windows" to R.drawable.ic_platform_windows,
        "Xbox" to R.drawable.ic_platform_xbox,
    )*/

    Column (
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(20.dp))

        Text(
            "Сортировать по",
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.whiteText),
            fontSize = 16.sp
        )
        val sel by viewModel.sortType.collectAsState()
        var selectedIndex by remember { mutableStateOf(if (sel) 0 else 1) }
        val options = listOf("Дате", "Названию")
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    modifier = Modifier,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Color.DarkGray,
                        inactiveContainerColor = Color.Transparent,
                        activeContentColor = Color.White,
                        inactiveContentColor = Color.DarkGray,
                        activeBorderColor = Color.Transparent,
                        inactiveBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex
                ) {
                    Text(
                        label,
                        fontSize = 14.sp,
                    )
                }
                Spacer(Modifier.width(5.dp))
            }
        }

/*        var startValue by remember { mutableStateOf(0f) }
        var endValue by remember { mutableStateOf(10f) }

        Spacer(Modifier.height(6.dp))
        Text(
            "Рейтинг",
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.whiteText),
            fontSize = 16.sp
        )

        CustomRangeSlider(
            range = 0f..10f,
            onValueChange = { start, end ->
                startValue = start
                endValue = end
            }
        )*/

        Spacer(Modifier.height(6.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Жанры",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.whiteText),
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier.clickable {
                    showGenres = !showGenres
                },
                text = if (showGenres) "Скрыть" else "Показать",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.whiteText),
                fontSize = 16.sp
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val genresToShow = if (showGenres) genres else selectedGenres
            genresToShow.forEach { genre ->
                FilterChip(
                    selected = selectedGenres.contains(genre),
                    onClick = {
                        selectedGenres = if (selectedGenres.contains(genre)) {
                            selectedGenres - genre
                        } else {
                            selectedGenres + genre
                        }
                        viewModel.getGameCount(selectedGenres, checked)
                    },
                    label = { Text(genre) }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Поддержка контроллера",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.whiteText),
                fontSize = 16.sp
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    viewModel.getGameCount(selectedGenres, it)
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.Red.copy(alpha = 0.6f)
                )
            )
        }


        Spacer(Modifier.height(20.dp))
        var showGames = "Игр нет"
        if (gameCount > 0) {
            showGames = "Показать $gameCount игры"
        }

        GradientButton(
            showGames,
            gameCount > 0,
            {
                viewModel.confirmFilter(selectedGenres, checked, selectedIndex)
                navController.popBackStack()
            },
            16.sp,
            colorResource(R.color.whiteText),
            FontWeight.Normal
        )
        Spacer(Modifier.height(50.dp))
    }
}