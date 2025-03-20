@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gameslibrary.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gameslibrary.MainActivity
import com.example.gameslibrary.ui.components.GradientButton
import com.example.gameslibrary.ui.components.MyTextField
import com.example.gameslibrary.ui.components.horizontalGradient
import com.example.gameslibrary.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen (onLoginSuccess = {
                startActivity(Intent(this, MainActivity::class.java))
            })
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen (onLoginSuccess = {})
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit ,viewModel: AuthViewModel = hiltViewModel()) {
    val user by viewModel.userAcc.collectAsState()
    val errorMessage by viewModel.error.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var showIntro by remember { mutableStateOf(true) }


    LaunchedEffect(user) {
        viewModel.loadUserProfile()
        if (user != null) {
            onLoginSuccess()
        }
        delay(2000L)
        showIntro = false
    }
    if (showIntro)
        IntroScreen()
    else {
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.radialGradient(
                    colors = listOf(Color.Black, colorResource(R.color.red)),
                    radius = 4000f
                )
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.02f),
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .padding(horizontal = screenHeight * 0.03f, vertical = screenHeight * 0.03f)
            ) {
                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
                Text(
                    text = "Log In",
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(R.color.blueIcon),
                                colorResource(R.color.redIcon)
                            )
                        ),
                        fontSize = 46.sp,
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.1f))

                var email by remember {
                    mutableStateOf("")
                }
                MyTextField(
                    email,
                    { email = it },
                    KeyboardOptions(keyboardType = KeyboardType.Email),
                    "something@example.com"
                )

                var pass by remember {
                    mutableStateOf("")
                }
                MyTextField(
                    pass,
                    { pass = it },
                    KeyboardOptions(keyboardType = KeyboardType.Password),
                    "Enter your password"
                )

                if (errorMessage != null) {
                    Text(text = errorMessage!!, color = Color.Red)
                }

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
                        GradientButton("Login", true, { viewModel.signIn(email, pass) },)
                        GradientButton("Create account", true, { viewModel.signUp(email, pass) })
                    }
                }
            }
        }
    }
}
