package com.mizani.labis.ui.screen.store

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mizani.labis.navigation.store.StoreNavigation
import com.mizani.labis.setBaseContentView
import com.mizani.labis.ui.screen.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {
            StoreNavigation(
                backListener = {
                    if (it) {
                        setResult(RESULT_OK)
                    }
                    finish()
                },
                navigateToProductActivity = { storeId ->
                    ProductActivity.startActivity(
                        this@StoreActivity,
                        storeId
                    )
                }
            )
        }
    }
}