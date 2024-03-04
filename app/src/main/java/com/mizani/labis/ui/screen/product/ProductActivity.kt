package com.mizani.labis.ui.screen.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mizani.labis.navigation.product.ProductNavigation
import com.mizani.labis.setBaseContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {
            ProductNavigation(
                storeId = intent.getLongExtra(STORE_ID, 0),
                backListener = {
                    finish()
                }
            )
        }
    }

    companion object {

        const val STORE_ID = "STORE_ID"

        fun startActivity(context: Context, storeId: Long) {
            context.startActivity(
                Intent(
                    context,
                    ProductActivity::class.java
                ).also {
                    it.putExtra(STORE_ID, storeId)
                }
            )
        }

    }

}