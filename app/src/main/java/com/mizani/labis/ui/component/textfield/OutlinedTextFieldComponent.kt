package com.mizani.labis.ui.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat

@Composable
fun OutlinedTextFieldComponent(
    value: String,
    label: String,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit
) {

    val isNumber = keyboardType == KeyboardType.Number

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (isNumber) {
                if (it.length <= 11) {
                    onChange.invoke(it.toNumberFormat())
                }
            } else {
                onChange.invoke(it)
            }
        },
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        maxLines = 1,
        enabled = isEnabled
    )
}

private fun String.toNumberFormat(): String {
    val numberFormat = DecimalFormat("###,###,###")
    if (isEmpty()) {
        return "0"
    }
    val number = this.replace(".", "").toInt()
    return numberFormat.format(number).replace(",", ".")
}