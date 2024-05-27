package com.mizani.labis.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.ui.component.TextFieldComponent
import com.mizani.labis.ui.component.TextFieldPasswordComponent
import com.mizani.labis.ui.theme.LabisTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onLoginClicked: (() -> Unit)? = null,
    navigateToRegister: (() -> Unit)? = null
) {

    var email = rememberSaveable { mutableStateOf("") }
    var password = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 34.sp,
                fontWeight = FontWeight.W700,
                color = Color.White
            )
        }
        Card(
            shape = RoundedCornerShape(topStart = 70.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .align(alignment = Alignment.Center),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W700
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 50.dp)
                )
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
                TextFieldPasswordComponent(text = password, imeAction = ImeAction.Done)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 20.dp)
                )
                Button(
                    onClick = {
                        onLoginClicked?.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 48.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .defaultMinSize(minHeight = 20.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Belum punya akun?",
                    )
                    Spacer(
                        modifier = Modifier
                            .width(5.dp)
                            .height(1.dp)
                    )
                    Text(
                        text = "Daftar Disini",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.clickable {
                            navigateToRegister?.invoke()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LoginScreenPreview() {
    LabisTheme {
        LoginScreen {

        }
    }
}