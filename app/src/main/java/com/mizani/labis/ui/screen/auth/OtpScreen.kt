package com.mizani.labis.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.mizani.labis.R

@Composable
fun OtpScreen(
    navController: NavController
) {

    var otp = rememberSaveable { mutableStateOf("") }
    val otpCount = 6

    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "OTP Verifikasi",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.White
            )
        }
        Column (
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.otp),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "OTP telah dikirim ke email anda",
                color = Color.White,
                fontWeight = FontWeight.W700,
                fontSize = 18.sp
            )
        }
        Card(
            shape = RoundedCornerShape(topStart = 70.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 320.dp)
                .align(Alignment.Center),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    BasicTextField(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        value = TextFieldValue(otp.value, selection = TextRange(otp.value.length)),
                        onValueChange = {
                            if (it.text.length <= otpCount) {
                                otp.value = it.text
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        decorationBox = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                repeat(otpCount) { index ->
                                    val char = when {
                                        index >= otp.value.length -> ""
                                        else -> otp.value[index]
                                    }
                                    val isFocused = otp.value.length == index
                                    Text(
                                        text = char.toString(),
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp, when {
                                                    isFocused -> Color.Black
                                                    else -> Color.Gray
                                                }, RoundedCornerShape(9.dp)
                                            )
                                            .padding(vertical = 15.dp)
                                            .width(40.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    if (index < otpCount - 1) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }

                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 48.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "Verifikasi",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}