package com.mizani.labis.ui.screen.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.mizani.labis.ui.screen.home.MainActivity
import com.mizani.labis.ui.screen.auth.navigation.AuthNavigation
import com.mizani.labis.setBaseContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {
            AuthNavigation {
                MainActivity.startActivity(this)
                finish()
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            Intent(context, AuthActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(it)
            }
        }
    }

}