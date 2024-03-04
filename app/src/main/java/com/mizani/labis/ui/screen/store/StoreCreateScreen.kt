package com.mizani.labis.ui.screen.store

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.data.dto.store.StoreDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.textfield.OutlinedTextFieldComponent
import com.mizani.labis.ui.theme.LabisTheme

@Composable
fun StoreCreateScreen(
    backListener: (() -> Unit)? = null,
    onSaveClicked: ((StoreDto) -> Unit)? = null
) {

    val storeName = rememberSaveable {
        mutableStateOf("")
    }

    val storeAddress = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.create_store),
                callback = backListener
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                label = stringResource(id = R.string.save),
                callback = {
                    onSaveClicked?.invoke(
                        StoreDto(
                            id = 0,
                            name = storeName.value,
                            address = storeAddress.value
                        )
                    )
                }
            )
        }
    }

}

@Preview
@Composable
private fun StoreCreateScreenPreview() {
    LabisTheme {
        Surface {
            StoreCreateScreen()
        }
    }
}