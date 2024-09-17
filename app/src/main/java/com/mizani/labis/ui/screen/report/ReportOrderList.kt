package com.mizani.labis.ui.screen.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.domain.model.dto.getTotalProductSold
import com.mizani.labis.ui.component.SearchComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.order.OrderViewOnlyComponent
import com.mizani.labis.utils.DialogUtils
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import com.mizani.labis.utils.StringUtils.toCurrency
import java.util.Date

@Composable
fun ReportOrderList(
    orderDto: List<OrderDto> = listOf(),
    date: Date = Date(),
    onPaidListener: (OrderDto) -> Unit = {},
    onBackListener: () -> Unit = {},
    onDeleteOrder: (OrderDto) -> Unit = {}
) {

    var isPaidDialogShown by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedOrder by rememberSaveable {
        mutableStateOf(OrderDto())
    }

    var searchKeyword by rememberSaveable {
        mutableStateOf("")
    }

    var totalDebt by rememberSaveable {
        mutableStateOf(0)
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

    LaunchedEffect(searchKeyword) {
        var totalDebtTemp = 0
        orderDto.forEachIndexed { _, item ->
            if (searchKeyword.lowercase()
                    .contains(item.name.lowercase()) || searchKeyword.isEmpty()
            ) {
                totalDebtTemp += calculateDebt(item.orders)
            }
        }
        totalDebt = totalDebtTemp
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
                Box(modifier = Modifier.padding(16.dp)) {
                    SearchComponent(
                        value = searchKeyword,
                        onChange = { value ->
                            searchKeyword = value
                        }
                    )
                }
//                DebtCalculateSection(totalDebt)
                TotalExpenditureAndDateSection(
                    totalProductSold = orderDto.getTotalProductSold(),
                    date = date
                )
                LazyColumn {
                    itemsIndexed(orderDto) { _, item ->
                        if (item.name.lowercase()
                                .contains(searchKeyword.lowercase()) || searchKeyword.isEmpty()
                        ) {
                            OrderReportItemComponent(
                                orderDto = item,
                                onClick = {
                                    if (item.isPayLater().not()) return@OrderReportItemComponent
                                    selectedOrder = item
                                    isPaidDialogShown = true
                                },
                                onDeleteListener = {
                                    onDeleteOrder.invoke(item)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

private fun calculateDebt(ordersDto: List<OrdersDto>): Int {
    return ordersDto.sumOf { it.price * it.count }
}

@Composable
private fun DebtCalculateSection(totalDebt: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = stringResource(id = R.string.total_debt),
            fontSize = 16.sp
        )
        Text(
            text = totalDebt.toCurrency(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun TotalExpenditureAndDateSection(totalProductSold: Int, date: Date = Date()) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.date),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = date.toReadableView(),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.total_product_sold),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = totalProductSold.toCurrency(""),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
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
    onClick: () -> Unit = {},
    onDeleteListener: () -> Unit = {}
) {

    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick.invoke() }
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = orderDto.name
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(text = orderDto.dateTime.toReadableView("dd MMM yyyy HH:mm"))
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        DialogUtils.confirmationDialog(
                            context,
                            context.getString(R.string.delete_order_title),
                            context.getString(R.string.delete_order_message),
                            positiveButtonText = context.getString(R.string.ok),
                            okListener = {
                                onDeleteListener.invoke()
                            }
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.total_price))
                    Spacer(modifier = Modifier.height(2.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = orderDto.totalPrice.toCurrency(),
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.total_profit))
                    Spacer(modifier = Modifier.height(2.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = orderDto.totalProfit.toCurrency(),
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            orderDto.orders.forEachIndexed { index, data ->
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    OrderViewOnlyComponent(
                        orderDto = data,
                        status = orderDto.status,
                    )
                    if (index != orderDto.orders.lastIndex) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color.Gray))
                    }
                }
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