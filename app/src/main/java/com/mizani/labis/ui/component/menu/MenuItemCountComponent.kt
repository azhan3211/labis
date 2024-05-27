package com.mizani.labis.ui.component.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.mizani.labis.ui.component.order.OrderItemCountComponent

@Composable
fun MenuItemCountComponent(
    count: Int = 0,
    onDec: () -> Unit,
    onInc: () -> Unit
) {

    val count = rememberSaveable {
        mutableStateOf(count)
    }

    OrderItemCountComponent(
        count = count.value,
        onDec = {
            if (count.value == 0) return@OrderItemCountComponent
            count.value = count.value - 1
            onDec.invoke()
        },
        onInc = {
            count.value = count.value + 1
            onInc.invoke()
        }
    )
}