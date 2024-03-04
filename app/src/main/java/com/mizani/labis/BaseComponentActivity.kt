package com.mizani.labis

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import com.mizani.labis.ui.theme.LabisTheme

fun ComponentActivity.setBaseContentView(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit
) {

    setContent {
        LabisTheme {
            content()
        }
    }

}