package com.mizani.labis.ui.component.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.ui.theme.LabisTheme

@Composable
fun MenuItemCount(
    onCountChange: (Int) -> Unit = {}
) {

    val count = rememberSaveable {
        mutableStateOf(0)
    }

    Card(
        shape = RoundedCornerShape(140.dp),
        modifier = Modifier
            .width(85.dp)
            .height(25.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 2.dp),
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
                    .shadow(
                        elevation = 1.dp,
                        shape = CircleShape
                    )
                    .clickable {
                        if (count.value == 0) return@clickable
                        count.value = count.value - 1
                        onCountChange.invoke(count.value)
                    },
                tint = Color.Black
            )
            Text(
                text = "${count.value}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            Icon(
                Icons.Filled.Add,
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .shadow(
                        elevation = 1.dp,
                        shape = CircleShape
                    )
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        count.value = count.value + 1
                        onCountChange.invoke(count.value)
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
            MenuItemCount()
        }
    }
}