package com.mizani.labis.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun TextFieldComponent(
    text: MutableState<String>,
    label: String,
    imeAction: ImeAction = ImeAction.Next
) {
    Card(
        backgroundColor = Color("#F8F6F4".toColorInt()),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
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
                            textField()
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val test = remember { mutableStateOf("Test") }
    TextFieldComponent(text = test, label = "Test")
}