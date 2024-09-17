package com.mizani.labis.ui.screen.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.textfield.OutlinedTextFieldComponent
import com.mizani.labis.utils.DialogUtils

@Composable
fun StoreDetailScreen(
    storeDto: StoreDto? = null,
    onUpdateListener: ((StoreDto) -> Unit)? = null,
    onProductClicked: ((Long) -> Unit)? = null,
    backListener: (() -> Unit)? = null
) {

    val context = LocalContext.current
    val storeName = rememberSaveable { mutableStateOf(storeDto?.name.orEmpty()) }
    val storeAddress = rememberSaveable { mutableStateOf(storeDto?.address.orEmpty()) }

    LaunchedEffect(storeDto) {
        storeDto?.let {
            storeName.value = it.name
            storeAddress.value = it.address
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.your_store)) {
                backListener?.invoke()
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = it.calculateTopPadding())
                    .padding(16.dp)
            ) {
                OutlinedTextFieldComponent(
                    value = storeName.value,
                    label = stringResource(id = R.string.store_name),
                    onChange = {
                        storeName.value = it
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextFieldComponent(
                    value = storeAddress.value,
                    label = stringResource(id = R.string.address),
                    onChange = {
                        storeAddress.value = it
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    label = stringResource(id = R.string.change)
                ) {
                    DialogUtils.confirmationDialog(
                        context = context,
                        title = context.getString(R.string.update_store),
                        message = context.getString(R.string.are_you_sure_want_to_update_this_store),
                        okListener = {
                            onUpdateListener?.invoke(
                                StoreDto(
                                    id = storeDto?.id ?: 0,
                                    name = storeName.value,
                                    address = storeAddress.value
                                )
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Menu(
                    name = stringResource(id = R.string.product),
                    Icons.Default.ProductionQuantityLimits,
                    callback = {
                        onProductClicked?.invoke(storeDto?.id ?: 0)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Menu(name = stringResource(id = R.string.product_category), Icons.Default.Category)
                Spacer(modifier = Modifier.height(10.dp))
                Menu(name = stringResource(id = R.string.employee), Icons.Default.PeopleAlt)
            }
        }
    )

}

@Composable
private fun Menu(name: String, icon: ImageVector, callback: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .clickable {
                callback.invoke()
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                icon,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
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
    StoreDetailScreen()
}