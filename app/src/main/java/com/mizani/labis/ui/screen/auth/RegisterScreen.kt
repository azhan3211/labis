package com.mizani.labis.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.mizani.labis.R
import com.mizani.labis.ui.screen.auth.navigation.AuthNavigationRoute
import com.mizani.labis.ui.component.TextFieldComponent
import com.mizani.labis.ui.component.TextFieldPasswordComponent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    navigateToLogin: () -> Unit
) {
    var email = rememberSaveable { mutableStateOf("") }
    var password = rememberSaveable { mutableStateOf("") }
    var repassword = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.primary)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Daftar",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White
                )
            }
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription= "",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.White
            )
        }
        Card(
            shape = RoundedCornerShape(topStart = 70.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .align(alignment = Alignment.Center),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextFieldComponent(
                    text = email.value,
                    label = stringResource(id = R.string.email),
                    onChange = {
                        email.value = it
                    }
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 20.dp)
                )
                TextFieldPasswordComponent(text = password)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 20.dp)
                )
                TextFieldPasswordComponent(
                    text = repassword,
                    label = stringResource(id = R.string.retry_password)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 20.dp)
                )
                Button(
                    onClick = {
                        navController.navigate(AuthNavigationRoute.OtpScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 48.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Daftar",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sudah punya akun?",
                    )
                    Spacer(modifier = Modifier
                        .width(5.dp)
                        .height(1.dp))
                    Text(
                        text = "Login Disini",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.clickable {
                            navigateToLogin()
                        }
                    )
                }
            }
        }
    }
}