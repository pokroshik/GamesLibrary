package com.example.gameslibrary.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderWithText(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    val gl = horizontalGradient()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Slider(
            steps = 9,
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent, // Прозрачный цвет стандартного thumb
                activeTrackColor = Color.Red.copy(alpha = 0.5f),
                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f),
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(30.dp) // Размер кастомного ползунка
                        .clip(CircleShape)
                        .background(gl)
                        .border(2.dp, Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.roundToInt().toString(), // Отображение текущего значения
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomRangeSlider(
    modifier: Modifier = Modifier,
    range: ClosedFloatingPointRange<Float> = 0f..100f,
    onValueChange: (Float, Float) -> Unit
) {
    val sliderState = remember {
        RangeSliderState(
            activeRangeStart = range.start,
            activeRangeEnd = range.endInclusive,
            valueRange = range,
            steps = 9
        )
    }

    RangeSlider(
        state = sliderState,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent, // Прозрачный стандартный ползунок
            activeTrackColor = Color.Red.copy(alpha = 0.6f),
            inactiveTrackColor = Color.Gray.copy(alpha = 0.3f),
            activeTickColor = Color.Transparent
        ),
        startThumb = { CustomThumb(sliderState.activeRangeStart) },
        endThumb = { CustomThumb(sliderState.activeRangeEnd) }
    )

    // Обновляем значения при изменении ползунков
    LaunchedEffect(sliderState.activeRangeStart, sliderState.activeRangeEnd) {
        onValueChange(sliderState.activeRangeStart, sliderState.activeRangeEnd)
    }
}

@Composable
fun CustomThumb(value: Float) {
    Box(
        modifier = Modifier
            .size(25.dp) // Размер кастомного ползунка
            .clip(CircleShape)
            .background(horizontalGradient())
            .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.roundToInt().toString(), // Отображение текущего значения
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

