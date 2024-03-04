package com.mizani.labis.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrderTotalPriceComponent() {

    Card(
        modifier = Modifier.shadow(10.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total harga",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = "Rp.1.000.000"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = ""
            )

        }
    }

}

@Preview
@Composable
private fun Preview() {
    OrderTotalPriceComponent()
}