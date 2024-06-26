package com.mizani.labis.ui.screen.product

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.product.CategoriesDropDownComponent
import com.mizani.labis.ui.component.product.UploadImageComponent
import com.mizani.labis.ui.component.textfield.OutlinedTextFieldComponent
import com.mizani.labis.utils.DialogUtils
import java.text.DecimalFormat


@Composable
fun ProductCreateScreen(
    storeId: Long = 0,
    categories: List<ProductCategoryDto> = arrayListOf(),
    onSaveProduct: ((ProductDto, ProductCategoryDto) -> Unit)? = null,
    backListener: (() -> Unit)? = null
) {

    val isActive = rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val productName = rememberSaveable { mutableStateOf("") }
    val price = rememberSaveable { mutableStateOf("0") }
    val priceSell = rememberSaveable { mutableStateOf("0") }
    val benefit = rememberSaveable { mutableStateOf(getBenefit(priceSell.value, price.value)) }
    val stock = rememberSaveable { mutableStateOf("0") }
    val newCategory = rememberSaveable { mutableStateOf("") }
    val isAddCategory = rememberSaveable { mutableStateOf(false) }
    var selectedCategory = ProductCategoryDto()

    val selectedCategoryIndex = rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.create_product),
                callback = backListener
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.active),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Switch(
                            checked = isActive.value,
                            onCheckedChange = {
                                isActive.value = isActive.value.not()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    CategoriesDropDownComponent(
                        categories = categories,
                        selectedCategoryIndex = selectedCategoryIndex.value,
                        isAddCategory = isAddCategory.value,
                        onCategorySelected = {
                            selectedCategoryIndex.value = it
                            if (it == 0) {
                                selectedCategory = ProductCategoryDto()
                            } else {
                                selectedCategory = categories[it - 1]
                            }
                        }
                    )
                    AddCategorySection(
                        selectedCategoryIndex = selectedCategoryIndex.value,
                        isAddCategory = isAddCategory.value,
                        newCategory = newCategory.value,
                        onAddClicked = {
                            isAddCategory.value = it
                            if (it.not()) {
                                selectedCategory = ProductCategoryDto()
                            }
                        },
                        onChangeNewCategoryText = {
                            newCategory.value = it
                            selectedCategory = ProductCategoryDto(
                                id = 0,
                                name = it,
                                storeId = storeId
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextFieldComponent(
                        value = productName.value,
                        label = stringResource(id = R.string.product_name),
                        onChange = {
                            productName.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        OutlinedTextFieldComponent(
                            value = price.value,
                            label = stringResource(id = R.string.capital),
                            modifier = Modifier.weight(1f),
                            keyboardType = KeyboardType.Number,
                            onChange = {
                                price.value = it
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        OutlinedTextFieldComponent(
                            value = priceSell.value,
                            label = stringResource(id = R.string.price_to_sell),
                            modifier = Modifier.weight(1f),
                            keyboardType = KeyboardType.Number,
                            onChange = {
                                priceSell.value = it
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    benefit.value = getBenefit(priceSell.value, price.value)
                    OutlinedTextFieldComponent(
                        value = benefit.value,
                        label = stringResource(id = R.string.profit),
                        isEnabled = false,
                        keyboardType = KeyboardType.Number,
                        onChange = {
                            benefit.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextFieldComponent(
                        value = stock.value,
                        label = stringResource(id = R.string.stock),
                        keyboardType = KeyboardType.Number,
                        onChange = {
                            stock.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    UploadImageComponent()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 16.dp)
                ) {
                    ButtonComponent(
                        label = stringResource(id = R.string.save),
                        modifier = Modifier.align(Alignment.Center),
                        callback = {
                            val product = ProductDto(
                                id = 0,
                                name = productName.value,
                                capital = price.value.replace(".", "").toInt(),
                                priceToSell = priceSell.value.replace(".", "").toInt(),
                                stock = stock.value.replace(".", "").toInt(),
                                storeId = storeId,
                                description = "",
                                categoryId = selectedCategory.id
                            )
                            onSaveProduct?.invoke(
                                product,
                                selectedCategory
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
internal fun AddCategorySection(
    selectedCategoryIndex: Int = 0,
    isAddCategory: Boolean = false,
    newCategory: String = "",
    onAddClicked: (Boolean) -> Unit = {},
    onChangeNewCategoryText: ((String) -> Unit) = {}
) {
    if (selectedCategoryIndex == 0) {
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextFieldComponent(
                value = newCategory,
                label = stringResource(id = R.string.new_category),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isEnabled = isAddCategory,
                onChange = onChangeNewCategoryText
            )
            Spacer(modifier = Modifier.width(16.dp))
            ButtonAddCategory(
                isAddCategory = isAddCategory,
                onAddClicked = onAddClicked
            )
        }
    }
}

@Composable
private fun ButtonAddCategory(
    isAddCategory: Boolean = false,
    onAddClicked: (Boolean) -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = if (isAddCategory) {
                Color.Red
            } else {
                Color("#2F58CD".toColorInt())
            },
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        ) {
            Icon(
                if (isAddCategory) {
                    Icons.Default.Remove
                } else {
                    Icons.Default.Add
                },
                contentDescription = "Add category icon",
                modifier = Modifier
                    .clickable {
                        onAddClicked.invoke(isAddCategory.not())
                    }
                    .height(16.dp)
                    .width(16.dp)
                    .size(16.dp),
                tint = Color.White
            )
        }
    }
}

private fun getBenefit(sell: String, price: String): String {
    val decimalFormat = DecimalFormat("###,###,###")
    val sellInt = sell.replace(".", "").toInt()
    val priceInt = price.replace(".", "").toInt()
    val result = sellInt - priceInt
    return decimalFormat.format(result).replace(",", ".")
}

@Preview
@Composable
private fun Preview() {
    ProductCreateScreen()
}