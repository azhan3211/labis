package com.mizani.labis.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mizani.labis.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onDismiss: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(3000L)
        onDismiss.invoke()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.app_name))
    }
}