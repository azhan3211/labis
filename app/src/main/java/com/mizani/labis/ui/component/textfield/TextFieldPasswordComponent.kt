package com.mizani.labis.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun TextFieldPasswordComponent(
    text: MutableState<String>,
    label: String = "Password",
    imeAction: ImeAction = ImeAction.Next
) {

    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        decorationBox = { textField ->
            Card(
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                ) {
                    Text(
                        text = label
                    )
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.value.isEmpty()) {
                            Text(
                                text = label,
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                                textField()
                            }
                            val image = if (passwordVisible.value) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }

                            val description = if (passwordVisible.value) {
                                "Hide password"
                            } else {
                                "Show password"
                            }

                            IconButton(onClick = {
                                passwordVisible.value = !passwordVisible.value
                            }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    }

                }
            }
        },
        textStyle = TextStyle(
            fontSize = 18.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        maxLines = 1,
        keyboardActions = KeyboardActions {
            if (imeAction == ImeAction.Done) {
                keyboardController?.hide()
            }
        },
        singleLine = true,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun Preview() {
    val text = remember { mutableStateOf("") }
    TextFieldPasswordComponent(text = text, label = "Password")
}