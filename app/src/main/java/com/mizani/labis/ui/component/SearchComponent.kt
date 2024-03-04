package com.mizani.labis.ui.component

import androidx.annotation.StringDef
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchComponent(
    value: MutableState<String>,
    hint: String = "Cari...",
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(40.dp))
            .then(modifier)
    ) {
        BasicTextField(
            value = value.value,
            onValueChange = {
                value.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (value.value.isEmpty()) {
                            Text(
                                text = hint,
                                color = Color.Gray
                            )
                        } else {
                            innerTextField()
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.clickable {

                        }
                    )
                }
            },
            keyboardActions = KeyboardActions {  },
            singleLine = true,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SearchComponent(rememberSaveable {
        mutableStateOf("test")
    })
}