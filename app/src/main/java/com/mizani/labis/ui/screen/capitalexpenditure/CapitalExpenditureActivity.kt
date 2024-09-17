package com.mizani.labis.ui.screen.capitalexpenditure

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mizani.labis.setBaseContentView
import com.mizani.labis.ui.screen.capitalexpenditure.navigation.CapitalExpenditureNavigation
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class CapitalExpenditureActivity : ComponentActivity() {

    private val date by lazy {
        intent.getLongExtra(SELECTED_DATE, Date().time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {
            CapitalExpenditureNavigation(
                Date(date),
                onBackListener = {
                    if (it) {
                        setResult(RESULT_OK)
                    }
                    finish()
                }
            )
        }
    }

    companion object {

        const val SELECTED_DATE = "SELECTED_DATE"

        fun startActivity(context: Context, date: Date = Date()) {
            val intent = getIntent(context, date)
            context.startActivity(intent)
        }

        fun getIntent(context: Context, date: Date = Date()): Intent {
            return Intent(context, CapitalExpenditureActivity::class.java).apply {
                putExtra(SELECTED_DATE, date.time)
            }
        }

    }

}