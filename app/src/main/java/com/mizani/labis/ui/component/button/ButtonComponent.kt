package com.mizani.labis.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun ButtonComponent(
    label: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    callback: (() -> Unit) = {}
) {
    Button(
        onClick = callback,
        modifier = modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(
            text = label.uppercase(),
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ButtonComponent("Button")
}