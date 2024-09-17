package com.mizani.labis.ui.screen.splash

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

@Composable
fun SplashScreen(
    token: String = "",
    onTokenRequest: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    LaunchedEffect(true) {
        if (token.isEmpty()) {
            onTokenRequest.invoke()
        } else {
            onDismiss.invoke()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.app_name))
    }
}