package com.mizani.labis.ui.screen.product

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.product.CategoriesDropDownComponent
import com.mizani.labis.ui.component.product.UploadImageComponent
import com.mizani.labis.ui.component.textfield.OutlinedTextFieldComponent
import java.text.DecimalFormat

@Composable
fun ProductEditScreen(
    productDto: ProductDto,
    categories: List<ProductCategoryDto> = arrayListOf(),
    onSaveProduct: ((ProductDto, ProductCategoryDto) -> Unit)? = null,
    onBackListener: () -> Unit = {}
) {

    val isActive = rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val productName = rememberSaveable { mutableStateOf(productDto.name) }
    val price = rememberSaveable { mutableStateOf(productDto.capital.toString()) }
    val priceSell = rememberSaveable { mutableStateOf(productDto.priceToSell.toString()) }
    val benefit = rememberSaveable { mutableStateOf(getBenefit(priceSell.value, price.value)) }
    val stock = rememberSaveable { mutableStateOf(productDto.stock.toString()) }
    val newCategory = rememberSaveable { mutableStateOf("") }
    val isAddCategory = rememberSaveable { mutableStateOf(false) }
    var selectedCategory = categories.find { it.id == productDto.categoryId } ?: ProductCategoryDto()

    val selectedCategoryIndex = rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(true) {
        categories.forEachIndexed() { index, item ->
            if (item.id == productDto.categoryId) {
                selectedCategoryIndex.value = index.inc()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.product_edit),
                callback = onBackListener
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
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
                                storeId = productDto.storeId
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
                                id = productDto.id,
                                name = productName.value,
                                capital = price.value.replace(".", "").toInt(),
                                priceToSell = priceSell.value.replace(".", "").toInt(),
                                stock = stock.value.replace(".", "").toInt(),
                                storeId = productDto.storeId,
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

private fun getBenefit(sell: String, price: String): String {
    val decimalFormat = DecimalFormat("###,###,###")
    val sellInt = sell.replace(".", "").toInt()
    val priceInt = price.replace(".", "").toInt()
    val result = sellInt - priceInt
    return decimalFormat.format(result).replace(",", ".")
}

@Preview
@Composable
private fun ProductEditScreenPreview() {
    Surface {
        ProductEditScreen(
            ProductDto()
        )
    }
}