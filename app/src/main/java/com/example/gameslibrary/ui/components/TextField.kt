package com.example.gameslibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R
import com.example.gameslibrary.ui.screens.formatLastSeen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldIconed(
    hint: String,
    lIcon: ImageVector,
    tIcon: ImageVector,
    size: TextUnit = 16.sp,
    shape: Dp = 10.dp,
    color: Color = colorResource(R.color.black1),
    border: Dp = 1.dp,

) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text,
        onValueChange = {text = it},
        placeholder = {
            Text(text = hint, color = Color.Gray, modifier = Modifier.fillMaxWidth(), fontSize = size)
        },
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = size
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.Transparent,
            unfocusedTextColor = Color.Transparent,
            cursorColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent
        ),
        leadingIcon = {
            if (lIcon != Icons.Filled.Build) {
                Icon(
                    imageVector = lIcon,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (lIcon != Icons.Filled.Build) {
                Icon(
                    imageVector = tIcon,
                    contentDescription = null
                )
            }
        },

        shape = RoundedCornerShape(shape),
        modifier = Modifier.fillMaxWidth().background(
            color = color,
            shape = RoundedCornerShape(shape)
        ) //.border(border, horizontalGradient(), RoundedCornerShape(shape))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    text: String,
    onTextChange: (String) -> Unit,
    keyboard: KeyboardOptions = KeyboardOptions.Default,
    hint: String,
    size: TextUnit = 16.sp,
    shape: Dp = 10.dp,
    color: Color = colorResource(R.color.black1),
    textColor: Color = Color.White,
    border: Dp = 0.dp,
    isSingle: Boolean = true,

    ) {
    OutlinedTextField(
        keyboardOptions = keyboard,
        value = text,
        onValueChange = {onTextChange(it)},
        placeholder = {
            Text(text = hint, color = Color.Gray, modifier = Modifier.fillMaxWidth(), fontSize = size)
        },
        singleLine = isSingle,
        textStyle = TextStyle(
            color = textColor,
            fontSize = size,
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

        shape = RoundedCornerShape(shape),
        modifier = Modifier.fillMaxWidth().then(
            if (border > 0.dp) Modifier.border(border, horizontalGradient(), RoundedCornerShape(shape))
            else Modifier
        ).background(
            color = color,
            shape = RoundedCornerShape(shape)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTextField(
    text: String,
    onTextChange: (String) -> Unit,
    keyboard: KeyboardOptions = KeyboardOptions.Default,
    hint: String = "",
    size: TextUnit = 16.sp,
    shape: Dp = 10.dp,
    color: Color = colorResource(R.color.searchBackground),
    textColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Normal,
    isSingle: Boolean = true,
    isEdited: Boolean = false,
    border: Dp = 0.dp,
    ) {
    if (isEdited) {
        MyTextField(text,onTextChange, keyboard, hint, size, shape, color, textColor, border, isSingle = isSingle)
    }
    else {
        Text(
            text,
            fontWeight = fontWeight,
            color = textColor,
            fontSize = size,
            textAlign = TextAlign.Center
        )
    }
}