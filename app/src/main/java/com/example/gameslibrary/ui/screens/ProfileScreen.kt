package com.example.gameslibrary.ui.screens


import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.data.models.UserModel
import com.example.gameslibrary.ui.components.CustomRangeSlider
import com.example.gameslibrary.ui.components.GameItem
import com.example.gameslibrary.ui.components.GradientButton
import com.example.gameslibrary.ui.components.MyDivider
import com.example.gameslibrary.ui.components.MyTextField
import com.example.gameslibrary.ui.components.ReviewItem
import com.example.gameslibrary.ui.components.SetTextField
import com.example.gameslibrary.ui.components.horizontalGradient
import com.example.gameslibrary.viewmodel.AuthViewModel
import com.example.gameslibrary.viewmodel.GameViewModel
import com.example.gameslibrary.viewmodel.TopBarViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    topBarViewModel: TopBarViewModel,
     viewModel: AuthViewModel = hiltViewModel(),
) {
    val uid = topBarViewModel.uid.value
    val isEdited by topBarViewModel.isEdited.collectAsState()
    val user by viewModel.user.collectAsState()
    var localUser by remember { mutableStateOf(user ?: UserModel()) }

    LaunchedEffect(uid) {
        viewModel.loadUser(uid)
        viewModel.loadUserGames(uid)
    }

    LaunchedEffect(user) {
        localUser = user ?: UserModel()
    }

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isEdited) {
                    Text(
                        "Никнейм",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.whiteText),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(Modifier.height(4.dp))
                }
                SetTextField(
                    localUser.login ?: "",
                    { newValue -> localUser = localUser.copy(login = newValue) },
                    hint = "Никнейм",
                    size = 24.sp,
                    keyboard = KeyboardOptions(keyboardType = KeyboardType.Text),
                    fontWeight = FontWeight.Bold,
                    isEdited = isEdited,
                    border = 1.dp
                )
                if (!isEdited) {
                    Text(
                        formatLastSeen(localUser.lastVisited),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.whiteText),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.searchBackground)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                )
                {
                    var showInfo by remember { mutableStateOf(false) }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Информация",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.whiteText),
                            fontSize = 16.sp
                        )
                        Text(
                            modifier = Modifier.clickable {
                                showInfo = !showInfo
                            },
                            text = if (showInfo) "Скрыть" else "Показать",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.whiteText),
                            fontSize = 16.sp
                        )
                    }
                    if (showInfo) {
                        var firstName = localUser.firstName
                        if (firstName == "")
                            firstName = null
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                        ) {
                            if (isEdited || firstName != null) {
                                Text(
                                    "Фамилия Имя",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )

                                Box(modifier = Modifier.weight(1.5f)) {
                                    SetTextField(
                                        localUser.firstName ?: "",
                                        { newValue ->
                                            localUser = localUser.copy(firstName = newValue)
                                        },
                                        hint = "Фамилия имя",
                                        size = 14.sp,
                                        keyboard = KeyboardOptions.Default,
                                        textColor = colorResource(R.color.whiteText),
                                        isEdited = isEdited,
                                        border = 1.dp
                                    )
                                }
                            }
                        }
                        if (isEdited || firstName != null)
                            MyDivider(color = Color.Gray)

                        var date = localUser.createdAt?.toDate()
                        var formattedDate = date?.let {
                            val dateFormatter =
                                SimpleDateFormat("d MMMM yyyy", Locale("ru"))
                            dateFormatter.timeZone = TimeZone.getDefault()
                            dateFormatter.format(it)
                        }

                        if (!isEdited) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Дата создания",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )
                                Text(
                                    formattedDate ?: "df",
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1.5f),
                                )
                            }
                            MyDivider(color = Color.Gray)
                        }
                        val country = localUser.country

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEdited || country != null)
                                Text(
                                    "Страна",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )

                            if (isEdited) {
                                var showCountryDialog by remember { mutableStateOf(false) }

                                Box(modifier = Modifier.weight(1.5f)) {
                                    GradientButton(
                                        country ?: "Выберите страну",
                                        true,
                                        { showCountryDialog = !showCountryDialog },
                                        16.sp,
                                        colorResource(R.color.whiteText),
                                        FontWeight.Normal
                                    )
                                }

                                if (showCountryDialog) {
                                    CountryPickerDialog(
                                        onCountrySelected = { newValue ->
                                            localUser = localUser.copy(country = newValue)
                                        },
                                        onDismiss = { showCountryDialog = false }
                                    )
                                }
                            } else {
                                if (country != null)
                                    Text(
                                        country,
                                        color = colorResource(R.color.whiteText),
                                        fontSize = 14.sp,
                                        modifier = Modifier.weight(1.5f),
                                    )
                            }
                        }
                        if (isEdited || country != null)
                            MyDivider(color = Color.Gray)

                        val gender = localUser.gender
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEdited || gender != null) {
                                Text(
                                    "Пол",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )
                            }

                            var expanded by remember { mutableStateOf(false) }
                            if (isEdited) {
                                Box(modifier = Modifier.weight(1.5f)) {
                                    GradientButton(
                                        localUser.gender ?: "Выберите пол",
                                        true,
                                        { expanded = true },
                                        16.sp,
                                        colorResource(R.color.whiteText),
                                        FontWeight.Normal
                                    )

                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        listOf(
                                            "Мужской",
                                            "Женский",
                                            "Анонимный"
                                        ).forEach { gender ->
                                            DropdownMenuItem(
                                                text = { Text(gender) },
                                                onClick = {
                                                    localUser = localUser.copy(gender = gender)
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            } else if (gender != null) {
                                Text(
                                    gender,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1.5f)
                                )
                            }
                        }
                        if (isEdited || gender != null)
                            MyDivider(color = Color.Gray)

                        val birthday = localUser.birthday
                        date = birthday?.toDate()

                        formattedDate = date?.let {
                            val dateFormatter =
                                SimpleDateFormat("d MMMM yyyy", Locale("ru")) //
                            dateFormatter.format(it)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEdited || formattedDate != null) {
                                Text(
                                    "Дата рождения",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            if (isEdited) {
                                var showDatePicker by remember { mutableStateOf(false) }
                                val datePickerState = rememberDatePickerState(
                                    initialSelectedDateMillis = localUser.birthday?.toDate()?.time
                                        ?: System.currentTimeMillis()
                                )
                                var tempSelectedDate by remember {
                                    mutableStateOf(
                                        datePickerState.selectedDateMillis
                                    )
                                }

                                Box(modifier = Modifier.weight(1.5f)) {
                                    GradientButton(
                                        formattedDate ?: "Выберите дату",
                                        true,
                                        { showDatePicker = !showDatePicker },
                                        16.sp,
                                        colorResource(R.color.whiteText),
                                        FontWeight.Normal
                                    )
                                }
                                if (showDatePicker) {
                                    DatePickerDialog(
                                        onDismissRequest = { showDatePicker = false },
                                        confirmButton = {
                                            TextButton(onClick = {
                                                datePickerState.selectedDateMillis?.let { millis ->
                                                    val date = Date(millis)
                                                    tempSelectedDate = millis
                                                    localUser =
                                                        localUser.copy(birthday = Timestamp(date))
                                                }
                                                showDatePicker = false
                                            }) {
                                                Text("OK")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = {
                                                showDatePicker = false
                                                datePickerState.selectedDateMillis =
                                                    tempSelectedDate
                                            }) {
                                                Text("Отмена")
                                            }
                                        }
                                    ) {
                                        DatePicker(
                                            state = datePickerState,
                                            showModeToggle = false
                                        )
                                    }
                                }
                            } else if (formattedDate != null) {
                                Text(
                                    formattedDate,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1.5f),
                                )
                            }
                        }
                        if (isEdited || formattedDate != null)
                            MyDivider(color = Color.Gray)

                        val steam = localUser.steamProfile
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEdited || steam != null) {
                                Text(
                                    "Профиль steam",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )
                                Box(modifier = Modifier.weight(1.5f)) {
                                    SetTextField(
                                        localUser.steamProfile ?: "",
                                        { newValue ->
                                            localUser = localUser.copy(steamProfile = newValue)
                                        },
                                        hint = "Ссылка на профиль",
                                        size = 14.sp,
                                        keyboard = KeyboardOptions.Default,
                                        textColor = colorResource(R.color.whiteText),
                                        isEdited = isEdited,
                                        isSingle = false,
                                        border = 1.dp
                                    )
                                }
                            }
                        }
                        if (isEdited || steam != null)
                            MyDivider(color = Color.Gray)

                        val bio = localUser.bio
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEdited || bio != null) {
                                Text(
                                    "О себе",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.whiteText),
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                )
                                Box(modifier = Modifier.weight(1.5f)) {
                                    SetTextField(
                                        localUser.bio ?: "",
                                        { newValue -> localUser = localUser.copy(bio = newValue) },
                                        hint = "О Себе",
                                        size = 14.sp,
                                        keyboard = KeyboardOptions.Default,
                                        textColor = colorResource(R.color.whiteText),
                                        isEdited = isEdited,
                                        isSingle = false,
                                        border = 1.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            MyDivider(color = Color.Gray)
            if (isEdited) {
                val context = LocalContext.current
                Spacer(Modifier.height(6.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    GradientButton(
                        "Сохранить изменения",
                        true,
                        {
                            viewModel.updateUserInfo(localUser)
                            topBarViewModel.changeProfile()
                        },
                        fontWeight = FontWeight.Normal
                    )
                    GradientButton(
                        "Выйти из аккаунта",
                        true,
                        {
                            viewModel.signOut()
                            topBarViewModel.changeProfile()
                        },
                        fontWeight = FontWeight.Normal
                    )
                    GradientButton(
                        "Удалить аккаунт",
                        true,
                        {
                            viewModel.deleteAccount()
                            topBarViewModel.changeProfile()
                        },
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
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

                FavoriteScreen(navController, viewModel, topBarViewModel)
            }
        }
    }
}


fun formatLastSeen(lastSeen: Timestamp?): String {
    lastSeen?.let {
        val calendar = Calendar.getInstance().apply {
            set(1999, Calendar.JANUARY, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val onlineTimestamp = Timestamp(calendar.time)

        if (lastSeen == onlineTimestamp) {
            return "В сети"
        }

        val now = Timestamp.now()
        val diffSeconds = (now.seconds - lastSeen.seconds)
        return when {
            diffSeconds < 60 -> "Недавно" // Менее минуты назад
            diffSeconds < 3600 -> "${diffSeconds / 60} мин. назад" // Менее часа назад
            diffSeconds < 86400 -> "${diffSeconds / 3600} ч. назад" // Менее дня назад
            else -> "${diffSeconds / 86400} дн. назад" // Менее месяца назад
        }
    }
    return ""
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun CountryPickerDialog(
    onDismiss: () -> Unit,
    onCountrySelected: (String) -> Unit
) {
        var searchQuery by remember { mutableStateOf("") }
    val countries = mapOf(
        "Европа" to listOf(
            "Австрия", "Албания", "Андорра", "Беларусь", "Бельгия", "Болгария", "Босния и Герцеговина",
            "Ватикан", "Венгрия", "Великобритания", "Германия", "Греция", "Дания", "Ирландия", "Исландия",
            "Испания", "Италия", "Кипр", "Косово", "Латвия", "Литва", "Лихтенштейн", "Люксембург", "Мальта",
            "Молдова", "Монако", "Нидерланды", "Норвегия", "Польша", "Португалия", "Россия", "Румыния", "Северная Македония",
            "Сербия", "Словакия", "Словения", "Украина", "Финляндия", "Франция", "Хорватия", "Черногория", "Чехия",
            "Швейцария", "Швеция", "Эстония"
        ),
        "Азия" to listOf(
            "Азербайджан", "Армения", "Афганистан", "Бангладеш", "Бахрейн", "Бруней", "Бутан", "Вьетнам", "Грузия",
            "Израиль", "Индия", "Индонезия", "Иордания", "Ирак", "Иран", "Йемен", "Казахстан", "Камбоджа", "Катар",
            "Кипр", "Китай", "Кувейт", "Кыргызстан", "Лаос", "Ливан", "Малайзия", "Мальдивы", "Монголия", "Мьянма",
            "Непал", "ОАЭ", "Оман", "Пакистан", "Палестина", "Саудовская Аравия", "Сингапур", "Сирия", "Таджикистан",
            "Таиланд", "Тимор-Лесте", "Туркменистан", "Турция", "Узбекистан", "Филиппины", "Шри-Ланка", "Южная Корея",
            "Япония"
        ),
        "Северная Америка" to listOf(
            "Антигуа и Барбуда", "Багамы", "Барбадос", "Белиз", "Гаити", "Гватемала", "Гондурас", "Гренада", "Доминика",
            "Доминиканская Республика", "Канада", "Коста-Рика", "Куба", "Мексика", "Никарагуа", "Панама", "Сальвадор",
            "США", "Сент-Винсент и Гренадины", "Сент-Китс и Невис", "Сент-Люсия", "Тринидад и Тобаго"
        ),
        "Южная Америка" to listOf(
            "Аргентина", "Боливия", "Бразилия", "Венесуэла", "Гайана", "Колумбия", "Парагвай", "Перу", "Суринам",
            "Уругвай", "Чили", "Эквадор"
        ),
        "Африка" to listOf(
            "Алжир", "Ангола", "Бенин", "Ботсвана", "Буркина-Фасо", "Бурунди", "Габон", "Гамбия", "Гана", "Гвинея",
            "Гвинея-Бисау", "ДР Конго", "Джибути", "Египет", "Замбия", "Зимбабве", "Кабо-Верде", "Камерун", "Кения",
            "Коморы", "Кот-д'Ивуар", "Лесото", "Либерия", "Ливия", "Маврикий", "Мавритания", "Мадагаскар", "Малави",
            "Мали", "Марокко", "Мозамбик", "Намибия", "Нигер", "Нигерия", "Руанда", "Сан-Томе и Принсипи", "Сенегал",
            "Сейшелы", "Сомали", "Судан", "Сьерра-Леоне", "Танзания", "Того", "Тунис", "Уганда", "ЦАР", "Чад", "Эритрея",
            "Эсватини", "Эфиопия", "Южный Судан", "ЮАР"
        ),
        "Океания" to listOf(
            "Австралия", "Вануату", "Кирибати", "Маршалловы Острова", "Микронезия", "Науру", "Новая Зеландия",
            "Палау", "Папуа — Новая Гвинея", "Самоа", "Соломоновы Острова", "Тонга", "Тувалу", "Фиджи"
        )
    )

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Выберите страну") },
            text = {
                Column {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск страны...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    LazyColumn {
                        countries.forEach { (continent, countryList) ->
                            val filteredList = countryList.filter {
                                it.contains(searchQuery, ignoreCase = true)
                            }
                            if (filteredList.isNotEmpty()) {
                                item { Text(text = continent, fontWeight = FontWeight.Bold) }
                                items(filteredList) { country ->
                                    Text(
                                        text = country,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onCountrySelected(country)
                                                onDismiss()
                                            }
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Закрыть")
                }
            }
        )

}