package com.mizani.labis.ui.component.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.ui.theme.LabisTheme
import com.mizani.labis.utils.StringUtils.toCurrency

@Composable
fun OrderViewOnlyComponent(
    status: String = OrderDto.CASH,
    orderDto: OrdersDto
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 14.dp),
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
                                text = (orderDto.price).toCurrency(),
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
                            text = "X${(orderDto.count).toCurrency("")}",
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.total_price),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = (orderDto.count * orderDto.price).toCurrency(),
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.total_profit),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = (orderDto.count * (orderDto.price - orderDto.capital)).toCurrency(),
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
//                Row {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.total_price),
//                            fontSize = 12.sp
//                        )
//                        Spacer(modifier = Modifier.height(2.dp))
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(30.dp),
//                            shape = RoundedCornerShape(8.dp),
//                            border = BorderStroke(1.dp, Color.Gray)
//                        ) {
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = (orderDto.count * orderDto.price).toCurrency(),
//                                    fontSize = 12.sp,
//                                    color = MaterialTheme.colors.secondary,
//                                    fontWeight = FontWeight.W600,
//                                    textAlign = TextAlign.Center,
//                                )
//                            }
//                        }
//                    }
//                    Spacer(modifier = Modifier.width(20.dp))
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.total_profit),
//                            fontSize = 12.sp
//                        )
//                        Spacer(modifier = Modifier.height(2.dp))
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(30.dp),
//                            shape = RoundedCornerShape(8.dp),
//                            border = BorderStroke(1.dp, Color.Gray)
//                        ) {
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = (orderDto.count * (orderDto.price - orderDto.capital)).toCurrency(),
//                                    fontSize = 12.sp,
//                                    color = Color.Green,
//                                    fontWeight = FontWeight.W600
//                                )
//                            }
//                        }
//                    }
//                }
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
private fun OrderViewOnlyComponentPreview() {
    LabisTheme {
        Surface {
            OrderViewOnlyComponent(
                orderDto = OrdersDto(
                    id = 0,
                    name = "Product 1",
                    capital = 1000,
                    count = 0,
                    price = 1000
                )
            )
        }
    }
}