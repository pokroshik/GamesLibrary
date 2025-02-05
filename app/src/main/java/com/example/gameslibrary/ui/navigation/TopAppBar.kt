package com.example.gameslibrary.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gameslibrary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNavigation(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
        ),
        title = {
            /*Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    "Centered ",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Cfsdasdasdsccccccccd",
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.whiteText)
                )
            }*/
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
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
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter),
                    contentDescription = "Localized description",
                    tint = colorResource(R.color.searchButton)
                )
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