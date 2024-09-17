package com.mizani.labis.ui.component.textfield

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.mizani.labis.utils.StringUtils.toNumberFormat

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onChange: (String) -> Unit
) {

    val isNumber = keyboardType == KeyboardType.Number

    Card(
        backgroundColor = Color("#F8F6F4".toColorInt()),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                if (isNumber) {
                    if (it.isEmpty()) {
                        onChange.invoke("0")
                    } else if (it.length <= 11) {
                        onChange.invoke(it.toNumberFormat())
                    }
                } else {
                    onChange.invoke(it)
                }
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
                            if (text.isEmpty()) {
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
                imeAction = imeAction,
                keyboardType = keyboardType
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
    TextFieldComponent(
        text = "test",
        label = "Test",
        onChange = {

        }
    )
}