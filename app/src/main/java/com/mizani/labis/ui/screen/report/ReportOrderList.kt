package com.mizani.labis.ui.screen.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.order.OrderComponent
import com.mizani.labis.utils.DialogUtils
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import java.util.Date

@Composable
fun ReportOrderList(
    orderDto: List<OrderDto> = listOf(),
    onPaidListener: (OrderDto) -> Unit = {},
    onBackListener: () -> Unit = {}
) {

    var isPaidDialogShown by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedOrder by rememberSaveable {
        mutableStateOf(OrderDto())
    }

    if (isPaidDialogShown) {
        PaidDialog(
            onOkListener = {
                onPaidListener.invoke(selectedOrder)
            },
            onDismissListener = {
                isPaidDialogShown = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.report_order_list),
                callback = onBackListener
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn {
                    itemsIndexed(orderDto) { index, item ->
                        OrderReportItemComponent(
                            orderDto = item,
                            onClick = {
                                if (item.isPayLater().not()) return@OrderReportItemComponent
                                selectedOrder = item
                                isPaidDialogShown = true
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun PaidDialog(
    onOkListener: () -> Unit,
    onDismissListener: () -> Unit
) {
    val context = LocalContext.current
    DialogUtils.confirmationDialog(
        context,
        title = stringResource(id = R.string.paid_order_title),
        message = stringResource(id = R.string.paid_order_message),
        okListener = {
            onOkListener.invoke()
        },
        dismissListener = {
            onDismissListener.invoke()
        }
    )
}

@Composable
private fun OrderReportItemComponent(
    orderDto: OrderDto,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = orderDto.name
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Text(text = orderDto.dateTime.toReadableView("dd MMM yyyy HH:mm"))
        }
        orderDto.orders.forEach {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                OrderComponent(
                    orderDto = it,
                    status = orderDto.status,
                    isCountShown = false
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderReportItemComponentPreview() {
    Surface {
        OrderReportItemComponent(
            orderDto = OrderDto(
                name = "Nama",
                dateTime = Date()
            )
        )
    }
}

@Preview
@Composable
private fun ReportOrderListPreview() {
    Surface {
        ReportOrderList(
            orderDto = listOf(
                OrderDto(
                    name = "Nama",
                    dateTime = Date(),
                    orders = listOf(
                        OrdersDto(
                            id = 0,
                            name = "Name",
                            capital = 0,
                            price = 0,
                            count = 0
                        ),
                        OrdersDto(
                            id = 0,
                            name = "Name",
                            capital = 0,
                            price = 0,
                            count = 0
                        )
                    )
                )
            )
        )
    }
}