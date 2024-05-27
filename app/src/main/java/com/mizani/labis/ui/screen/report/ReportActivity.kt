package com.mizani.labis.ui.screen.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mizani.labis.setBaseContentView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ReportActivity : ComponentActivity() {

    private val startDate by lazy {
        intent.getLongExtra(REPORT_START_DATE, 0L)
    }

    private val endDate by lazy {
        intent.getLongExtra(REPORT_END_DATE, 0L)
    }

    private val category by lazy {
        intent.getStringExtra(REPORT_CATEGORY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {

            val reportViewModel: ReportViewModel = hiltViewModel()

            LaunchedEffect(category) {
                when (category) {
                    REPORT_ALL -> {
                        reportViewModel.getOrder(
                            Date(startDate),
                            Date(endDate)
                        )
                    }

                    REPORT_PAID -> {
                        reportViewModel.getOrderPaid(
                            Date(startDate),
                            Date(endDate)
                        )
                    }

                    REPORT_UNPAID -> {
                        reportViewModel.getOrderUnpaid(
                            Date(startDate),
                            Date(endDate)
                        )
                    }
                }
            }

            ReportOrderList(
                orderDto = reportViewModel.orderPaidSaved,
                onPaidListener = {
                    reportViewModel.paidDebt(it)
                },
                onBackListener = {
                    finish()
                }
            )
        }
    }

    companion object {

        private const val REPORT_START_DATE = "REPORT_ DATE"
        private const val REPORT_END_DATE = "REPORT_ DATE"
        private const val REPORT_CATEGORY = "REPORT_CATEGORY"
        const val REPORT_UNPAID = "REPORT_UNPAID"
        const val REPORT_PAID = "REPORT_PAID"
        const val REPORT_ALL = "REPORT_ALL"
        const val REPORT_MONTHLY = "REPORT_MONTHLY"

        fun startActivity(
            context: Context,
            category: String,
            startDate: Date = Date(),
            endDate: Date = Date()
        ) {
            Intent(context, ReportActivity::class.java).apply {
                this.putExtra(REPORT_CATEGORY, category)
                this.putExtra(REPORT_START_DATE, startDate.time)
                this.putExtra(REPORT_END_DATE, endDate.time)
                context.startActivity(this)
            }
        }

    }

}