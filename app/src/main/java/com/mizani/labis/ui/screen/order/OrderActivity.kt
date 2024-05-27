package com.mizani.labis.ui.screen.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.setBaseContentView
import com.mizani.labis.ui.screen.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderActivity : ComponentActivity() {

    private val orders by lazy {
        intent.getSerializableExtra(ORDER_DATA) as Array<OrdersDto>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView {

            val orderViewModel: OrderViewModel = hiltViewModel()

            LaunchedEffect(true) {
                orderViewModel.setOrders(orders.toList())
            }

            LaunchedEffect(orderViewModel.isSaved.value) {
                if (orderViewModel.isSaved.value) {
                    Intent(this@OrderActivity, MainActivity::class.java).apply {
                        this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                    startActivity(Intent(this@OrderActivity, MainActivity::class.java))
                    finish()
                }
            }

            LaunchedEffect(orderViewModel.isTotalOrderZero.value) {
                if (orderViewModel.isTotalOrderZero.value) {
                    finish()
                }
            }

            OrderReviewScreen(
                orders = orderViewModel.orders,
                totalPrice = orderViewModel.totalPrice.value,
                totalOrder = orderViewModel.totalOrder.value,
                onSave = {
                    orderViewModel.onSave(it)
                },
                onInc = {
                    orderViewModel.onOrderInc(it)
                },
                onDec = {
                    orderViewModel.onOrderDec(it)
                },
                backListener = {
                    finish()
                },
            )
        }
    }

    companion object {

        private const val ORDER_DATA = "ORDER_DATA"

        fun startActivity(context: Context, orders: Array<OrdersDto>) {
            Intent(context, OrderActivity::class.java).apply {
                this.putExtra(ORDER_DATA, orders)
                context.startActivity(this)
            }
        }

    }

}