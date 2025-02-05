@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gameslibrary.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameslibrary.R
import androidx.compose.ui.platform.LocalConfiguration
import com.example.gameslibrary.MainActivity
import com.example.gameslibrary.ui.components.MyTextField
import com.example.gameslibrary.ui.components.horizontalGradient

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen (onLoginClick = {
                startActivity(Intent(this, MainActivity::class.java))
            })
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen (onLoginClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick:()->Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    var filledText by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Brush.radialGradient(
            colors = listOf(Color.Black, colorResource(R.color.red)),
            radius = 4000f
        ))
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.02f),
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = screenHeight * 0.03f, vertical = screenHeight * 0.03f)
        ){
            Spacer(modifier = Modifier.height(screenHeight * 0.03f))
            Text(
                text = "Log In",
                style = TextStyle(
                    brush = Brush.verticalGradient(colors = listOf(colorResource(R.color.blueIcon), colorResource(R.color.redIcon))),
                    fontSize = 46.sp,
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(screenHeight * 0.1f))

            var emailText by remember {
                mutableStateOf("")
            }

                OutlinedTextField(
                    value = emailText,
                    onValueChange = {emailText = it},
                    placeholder = {
                        Text(text = "something@example.com", color = Color.Gray, modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.Transparent,
                        unfocusedTextColor = Color.Transparent,
                        cursorColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, horizontalGradient(), RoundedCornerShape(10.dp)).background(
                        color = colorResource(R.color.black1),
                        shape = RoundedCornerShape(10.dp)
                    )
                )

                MyTextField("Enter your password")

            Text(
                text = "Forget your password?",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.02f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GradientButton("Login", onLoginClick,)
                    GradientButton("Create account", onLoginClick,)
                }
            }
        }
    }
}

@Composable
fun GradientButton(
    text:String,
    onClick: ()->Unit,
) {
    Button(
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
        Text(text=text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}
