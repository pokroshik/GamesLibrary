package com.example.gameslibrary.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R

@Composable
fun GradientButton1(
    horizontalDp: Dp = 6.dp,
    hPadding: Dp = 10.dp,
    vPadding: Dp = 4.dp,
    shapeDp: Dp = 16.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = Modifier.border(width = 1.dp, brush = horizontalGradient(), shape = RoundedCornerShape(shapeDp))
    ) {
        TextButton(content = content)
    }
}

@Composable
fun GradientButton(
    text:String,
    enabled: Boolean = true,
    onClick: ()->Unit,
    size: TextUnit = 22.sp,
    color: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Button(
        enabled = enabled,
        onClick=onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(colorResource(R.color.blueIcon), colorResource(R.color.redIcon))
            )
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Text(
            text=text,
            fontSize = size,
            fontWeight = fontWeight,
            color = if (enabled) color else Color.DarkGray
            )
    }
}

@Composable
fun ClassicButton(
    contentColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.searchButton),
            contentColor = contentColor
        ),
        onClick = { },
        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
    ) {
        content()
    }
}

@Composable
fun TextButton(
    horizontalDp: Dp = 6.dp,
    hPadding: Dp = 10.dp,
    vPadding: Dp = 4.dp,
    shapeDp: Dp = 16.dp,
    color: Color = colorResource(R.color.searchButton),
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(shapeDp)
            )
            .padding(horizontal = hPadding, vertical = vPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(horizontalDp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
fun MyDivider(
    thickness: Dp = 1.dp,
    brush: Brush = Brush.horizontalGradient(
        listOf(colorResource(R.color.redIcon), colorResource(R.color.blueIcon))
    ),
    color: Color = Color.Cyan,
    isHorizontal: Boolean = true,
    vPadding: Dp = 0.dp
) {
    Box(
        modifier = Modifier.padding(vertical = vPadding).then(
            if (color == Color.Cyan) Modifier.background(brush) else Modifier.background(color)
        ).then(
            if (isHorizontal) Modifier.height(thickness).fillMaxWidth()
            else Modifier.width(thickness).fillMaxHeight()
        )
    )
}