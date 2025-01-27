package com.example.gameslibrary.Activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gameslibrary.MainActivity
import com.example.gameslibrary.R

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen (onLoginClick = {
                startActivity(Intent(this,MainActivity::class.java))
            })
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen (onLoginClick = {})
}

@Composable
fun LoginScreen(onLoginClick:()->Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Brush.radialGradient(
            colors = listOf(Color.Black, colorResource(R.color.red)),
            radius = 4000f
        ))
    ) {
        Column (
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 32.dp, vertical = 16.dp)
        ){
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Log In",
                style = TextStyle(
                    brush = Brush.verticalGradient(colors = listOf(colorResource(R.color.blueIcon), colorResource(R.color.redIcon))),
                    fontSize = 46.sp,
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(200.dp))
            GradientTextField(
                hint = "something@example.com",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            GradientTextField(
                hint = "Enter your Password",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Forget your password?",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(220.dp))
            GradientButton("Login", onLoginClick, )
            Spacer(modifier = Modifier.height(16.dp))
            GradientButton("Create account", onLoginClick,)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientTextField(
    hint:String,
    modifier: Modifier=Modifier,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default
) {
    Box(
        modifier = modifier.height(55.dp).background(
            brush = Brush.linearGradient(
                colors = listOf(colorResource(R.color.blueIcon), colorResource(R.color.redIcon))
            ),
            shape = RoundedCornerShape(10.dp)
        ).padding(1.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = hint, color = Color.Gray, modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)
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
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth().background(
                color = colorResource(R.color.black1),
                shape = RoundedCornerShape(10.dp)
            ).align(Alignment.Center)
        )
    }
}