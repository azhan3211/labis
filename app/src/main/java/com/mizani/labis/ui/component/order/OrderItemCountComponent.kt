package com.mizani.labis.ui.component.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.ui.theme.LabisTheme

@Composable
fun OrderItemCountComponent(
    count: Int = 0,
    onInc: () -> Unit = {},
    onDec: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(140.dp),
        modifier = Modifier
            .width(85.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Remove,
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        onDec.invoke()
                    },
                tint = Color.Black
            )
            Text(
                text = "$count",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Icon(
                Icons.Filled.Add,
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        onInc.invoke()
                    },
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun MenuItemCountPreview() {
    LabisTheme {
        Surface {
            OrderItemCountComponent()
        }
    }
}