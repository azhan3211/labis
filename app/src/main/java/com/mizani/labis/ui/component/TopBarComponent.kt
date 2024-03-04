package com.mizani.labis.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    callback: (() -> Unit)? = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back button",
            tint = Color.Black,
            modifier = Modifier.clickable {
                callback?.invoke()
            }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TopBarComponent("Labis")
}