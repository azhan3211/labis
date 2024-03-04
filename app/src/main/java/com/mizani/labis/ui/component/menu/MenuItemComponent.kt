package com.mizani.labis.ui.component.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.utils.StringUtils.toCurrency

@Composable
fun MenuItemComponent(
    isCountShown: Boolean = true,
    productDto: ProductDto,
    callback: (Int) -> Unit = {}
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
                Text(
                    text = productDto.name,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = (productDto.priceToSell).toCurrency(),
                    fontSize = 18.sp,
                    color = Color("#47A992".toColorInt()),
                    fontWeight = FontWeight.W600
                )
                Spacer(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = productDto.description,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    if (isCountShown) {
                        MenuItemCount(
                            onCountChange = callback
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun MenuPreview() {
    MenuItemComponent(
        productDto = ProductDto(
            id = 0,
            name = "Product 1",
            capital = 1000,
            storeId = 1,
            stock = 1,
            description = "Lorem ipsum dolor amet",
            priceToSell = 2000,
            categoryId = 0
        )
    ) {}
}