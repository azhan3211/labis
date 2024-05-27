package com.mizani.labis.ui.component.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.utils.StringUtils.toCurrency

@Composable
fun OrderComponent(
    isCountShown: Boolean = true,
    status: String = OrderDto.CASH,
    orderDto: OrdersDto,
    onInc: () -> Unit = {},
    onDec: () -> Unit = {}
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.otp),
                contentDescription = "",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = orderDto.name,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Row {
                            Text(
                                text = "${(orderDto.price).toCurrency()} X ${orderDto.count}",
                                fontSize = 14.sp,
                                color = Color("#47A992".toColorInt()),
                                fontWeight = FontWeight.W600
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "${(orderDto.count * orderDto.price).toCurrency()}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.W600
                        )
                        Text(
                            text = getStatus(status),
                            fontSize = 18.sp,
                            color = if (status == OrderDto.UNPAID) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                            fontWeight = FontWeight.W600
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    if (isCountShown) {
                        OrderItemCountComponent(
                            count = orderDto.count,
                            onInc = onInc,
                            onDec = onDec,
                        )
                    }
                }
            }
        }
    }
}

private fun getStatus(status: String) = when (status) {
    OrderDto.PAID -> "LUNAS"
    OrderDto.UNPAID -> "BELUM BAYAR"
    else -> ""
}

@Preview
@Composable
fun OrderComponentPreview() {
    OrderComponent(
        orderDto = OrdersDto(
            id = 0,
            name = "Product 1",
            capital = 1000,
            count = 0,
            price = 0
        )
    ) {}
}