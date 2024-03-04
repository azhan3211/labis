package com.mizani.labis.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.mizani.labis.R
import com.mizani.labis.data.dto.store.StoreDto

@Composable
fun StoreItemComponent(
    store: StoreDto,
    onSelectListener: ((StoreDto) -> Unit)? = null,
    onDetailListener: ((StoreDto) -> Unit)? = null,
    onDeleteListener: ((StoreDto) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = store.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            ButtonCustom(
                stringResource(id = R.string.detail),
                callback = {
                    onDetailListener?.invoke(store)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            ButtonCustom(
                stringResource(id = R.string.select),
                backgroundColor = Color("#00DFA2".toColorInt()),
                callback = {
                    onSelectListener?.invoke(store)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.Outlined.Delete,
                contentDescription = "",
                modifier = Modifier.clickable {
                    onDeleteListener?.invoke(store)
                }
            )
        }
    }

}

@Composable
private fun ButtonCustom(
    label: String,
    backgroundColor: Color = Color("#2F58CD".toColorInt()),
    modifier: Modifier = Modifier,
    callback: (() -> Unit)? = {}
) {
    Button(
        onClick = {
            callback?.invoke()
        },
        modifier = Modifier
            .width(70.dp)
            .height(30.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.W700
        )
    }
}

@Preview
@Composable
private fun Preview() {
    StoreItemComponent(
        StoreDto(
            id = 0,
            name = "Name",
            address = "Address"
        )
    )
}