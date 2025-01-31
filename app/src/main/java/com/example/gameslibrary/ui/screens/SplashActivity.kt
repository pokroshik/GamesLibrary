@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gameslibrary.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IntroScreen(onGetInClick = {
                startActivity(Intent(this, LoginActivity::class.java))
            })
        }
    }
}

@Composable
@Preview
fun IntroScreenPreview() {
    IntroScreen(onGetInClick={})
}

@Composable
fun IntroScreen(onGetInClick: ()->Unit){
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colorResource(R.color.red1), colorResource(R.color.blueIcon))
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding().background(
            Brush.radialGradient(
                colors = listOf(Color.Black, colorResource(R.color.red)),
                radius = 4000f
            )
        )
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(id= R.drawable.circle),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.7f).drawWithContent {
                drawContent()
                drawCircle(
                    brush = gradientBrush,
                    radius = size.minDimension / 2,
                    center = Offset(size.width / 2, size.height / 2),
                    style = Stroke(width = size.minDimension / 2 * 0.02f)
                )
            }
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(0.5f).verticalScroll(rememberScrollState())
        ){
            Text(
                text = "Gamer Library",
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.blueIcon),
                            colorResource(R.color.redIcon)
                        )
                    ),
                    fontSize = 46.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Вся коллекция игр \nв одном приложении",
                style = TextStyle(
                    color = colorResource(R.color.whiteText),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.weight(0.15f))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(50.dp),
                onClick = onGetInClick,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(R.color.red1),
                            colorResource(R.color.blueIcon)
                        )
                    )
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Продолжить", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }
    }
}