package com.example.gameslibrary.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gameslibrary.R

@Composable
fun GradientIcon(
    size: Dp,
    drawableRes: Int,
    gradientBrush: Brush = Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    )
) {

    MyIcon(size = size,
        drawableRes = drawableRes,
        modifier = Modifier.graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = gradientBrush,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
    )

}

@Composable
fun GradientIconVector(
    size: Dp,
    vector: ImageVector,
    gradientBrush: Brush = Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    )
) {

    MyIconVector(size = size,
        vector = vector,
        modifier = Modifier.graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = gradientBrush,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
    )

}

@Composable
fun CrossedIcon(
    thickness: Dp = 1.dp,
    sizeDp: Dp,
    drawableRes: Int = 0,
) {
    val density = LocalDensity.current.density
    val gradientBrush = horizontalGradient()
    // Задаём толщину линии в dp (например, 4.dp)
    val lineWidthInPx = thickness.value * density
    Box(
        modifier = Modifier.size(sizeDp)
    ) {
        MyIcon(size = sizeDp, drawableRes = drawableRes)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val start = Offset(0f, 0f) // Начало линии (верхний левый угол)
            val end = Offset(size.width, size.height) // Конец линии (нижний правый угол)

            drawLine(
                brush = gradientBrush,
                start = start,
                end = end,
                strokeWidth = lineWidthInPx // Толщина линии
            )
        }
    }
}

@Composable
fun MyIcon(
    size: Dp,
    drawableRes: Int,
    color: Color = Color.White,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        modifier = modifier.size(size),
        tint =  color,
    )
}

@Composable
fun MyIconVector(
    size: Dp,
    vector: ImageVector,
    color: Color = Color.White,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = vector,
        contentDescription = null,
        modifier = modifier.size(size),
        tint =  color,
    )
}

