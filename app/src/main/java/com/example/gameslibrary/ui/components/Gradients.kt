package com.example.gameslibrary.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.gameslibrary.R

@Composable
fun horizontalGradient(
): Brush {
    return Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    )
}