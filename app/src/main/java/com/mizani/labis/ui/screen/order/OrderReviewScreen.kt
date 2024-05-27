package com.mizani.labis.ui.screen.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.order.OrderComponent
import com.mizani.labis.ui.component.order.OrderDialog
import com.mizani.labis.utils.StringUtils.toCurrency
import com.mizani.labis.utils.StringUtils.toDecimalFormat

@Composable
fun OrderReviewScreen(
    orders: List<OrdersDto>,
    totalPrice: Int = 0,
    totalOrder: Int = 0,
    onInc: (OrdersDto) -> Unit = {},
    onDec: (OrdersDto) -> Unit = {},
    onSave: (OrderDto) -> Unit = {},
    backListener: () -> Unit = {}
) {

    var isShowDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (isShowDialog) {
        OrderDialog(
            totalPrice = totalPrice,
            setShowDialog = {
                isShowDialog = false
            },
            onSave = {
                val isPayLater = it.isNullOrEmpty().not()
                val orderDto = OrderDto(
                    name = if (isPayLater.not()) "Pelanggan" else it,
                    status = if (isPayLater) OrderDto.UNPAID else OrderDto.CASH
                )
                onSave(orderDto)
            }
        )
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.orders),
                callback = backListener
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it.calculateTopPadding())
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(orders) { index, item ->
                        Spacer(modifier = Modifier.height(16.dp))
                        OrderComponent(
                            orderDto = item,
                            onDec = {
                                onDec.invoke(item)
                            },
                            onInc = {
                                onInc.invoke(item)
                            }
                        )
                        if (index == orders.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
                PriceSummarySection(
                    totalPrice,
                    totalOrder,
                    onSaveClicked = {
                        isShowDialog = isShowDialog.not()
                    }
                )
            }
        }
    )
}

@Composable
private fun PriceSummarySection(
    totalPrice: Int,
    totalOrder: Int,
    onSaveClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.total_price),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = totalPrice.toCurrency(),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.total_order),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = "${totalOrder.toDecimalFormat()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                label = stringResource(id = R.string.save),
                callback = {
                    onSaveClicked.invoke()
                }
            )
        }
    }
}

@Preview
@Composable
private fun OrderScreenPreview() {
    OrderReviewScreen(
        orders = listOf(
            OrdersDto(
                id = 0,
                price = 1000,
                count = 2,
                name = "Product",
                capital = 1000
            )
        )
    )
}