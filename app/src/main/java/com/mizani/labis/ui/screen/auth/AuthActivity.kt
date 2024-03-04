package com.mizani.labis.ui.screen.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mizani.labis.ui.screen.home.MainActivity
import com.mizani.labis.ui.screen.auth.navigation.AuthNavigation
import com.mizani.labis.setBaseContentView

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {
            AuthNavigation {
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
            }
        }
    }

}