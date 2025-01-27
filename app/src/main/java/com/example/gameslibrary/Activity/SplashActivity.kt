package com.example.gameslibrary.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
                startActivity(Intent(this,LoginActivity::class.java))
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(
                colors = listOf(Color.Black, colorResource(R.color.red)),
                radius = 4000f // Радиус градиента
            ))
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            HeaderSection()
            FooterSection(onGetInClick)
        }
    }

}

@Composable
fun HeaderSection() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colorResource(R.color.red1), colorResource(R.color.blueIcon))
    )
    Box(
        modifier = Modifier.fillMaxWidth().height(650.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.matchParentSize()
        ) {
            Image(
                painter = painterResource(id= R.drawable.circle),
                contentDescription = null,
                modifier = Modifier.size(300.dp).drawWithContent {
                    drawContent()
                    drawCircle(
                        brush = gradientBrush,
                        radius = size.minDimension / 2,
                        center = Offset(size.width / 2, size.height / 2),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text="Gamer Library",
                style = TextStyle(
                    brush = Brush.verticalGradient(colors = listOf(colorResource(R.color.blueIcon), colorResource(R.color.redIcon))),
                    fontSize = 46.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text="Вся коллекция игр \nв одном приложении",
                style = TextStyle(
                    color = colorResource(R.color.whiteText),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
@Composable
fun FooterSection(onGetInClick: ()->Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp)
    ) {
        Button(
            modifier = Modifier.size(200.dp,50.dp).align(Alignment.Center),
            shape = RoundedCornerShape(50.dp),
            onClick = onGetInClick,
            border = BorderStroke(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(colorResource(R.color.red1), colorResource(R.color.blueIcon))
                )
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Text(text = "Продолжить", color = Color.White, fontSize = 18.sp)
        }
    }
}