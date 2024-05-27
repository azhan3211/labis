package com.mizani.labis.ui.screen.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.mizani.labis.setBaseContentView
import com.mizani.labis.ui.screen.order.OrderActivity
import com.mizani.labis.ui.screen.report.ReportActivity
import com.mizani.labis.ui.screen.store.StoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {

            val storeChanged = rememberSaveable {
                mutableStateOf(false)
            }

            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) {
                    storeChanged.value = true
                }
            }

            HomeScreen(
                storeChanged = storeChanged.value,
                resetStoreChanged = {
                    storeChanged.value = false
                },
                navigateToStoreActivity = {
                    launcher.launch(
                        StoreActivity.getIntent(this@MainActivity)
                    )
                },
                navigateToOrderActivity = {
                    OrderActivity.startActivity(this, it.toTypedArray())
                },
                navigateToReportActivity = { startDate, endDate, category ->
                    ReportActivity.startActivity(this, category, startDate, endDate)
                }
            )
        }
    }
}