package com.mizani.labis.ui.screen.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.mizani.labis.setBaseContentView
import com.mizani.labis.ui.screen.auth.AuthActivity
import com.mizani.labis.ui.screen.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {

            val viewModel: SplashViewModel = hiltViewModel()

            SplashScreen(
                token = viewModel.getToken(),
                onTokenRequest = {
                    viewModel.requestToken()
                    AuthActivity.startActivity(this)
                },
                onDismiss = {
                    if (viewModel.isLogin().not()) {
                        AuthActivity.startActivity(this)
                    } else {
                        MainActivity.startActivity(this)
                    }
                }
            )
        }
    }

}