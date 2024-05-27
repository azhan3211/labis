package com.mizani.labis.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ProductionQuantityLimits
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.ui.component.SearchComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.ui.component.menu.MenuItemEditComponent
import com.mizani.labis.ui.theme.LabisTheme

@Composable
fun ProductListScreen(
    products: List<ProductDto> = arrayListOf(),
    onDeleteListener: (ProductDto) -> Unit = {},
    onAddProductListener: (() -> Unit)? = null,
    onProductClicked: (ProductDto) -> Unit = {},
    backListener: (() -> Unit)? = null
) {

    val searchKeyword = rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.list_product),
                callback = backListener
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            if (products.isEmpty()) {
                EmptyProductSection()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        SearchComponent(
                            value = searchKeyword.value,
                            onChange = {
                                searchKeyword.value = it
                            },
                            hint = stringResource(id = R.string.search_product)
                        )
                    }
                    LazyColumn {
                        itemsIndexed(products) { index, item ->
                            if (index == 0) {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            Box(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                MenuItemEditComponent(
                                    productDto = item,
                                    onDeleteListener = onDeleteListener,
                                    onClick = {
                                        onProductClicked.invoke(it)
                                    }
                                )
                            }
                            Spacer(
                                modifier = Modifier.height(
                                    if (index == products.lastIndex) {
                                        150.dp
                                    } else {
                                        10.dp
                                    }
                                )
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                backgroundColor = Color.Blue,
                onClick = {
                    onAddProductListener?.invoke()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-20).dp, (-30).dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add product icon",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
internal fun EmptyProductSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.ProductionQuantityLimits,
            contentDescription = "Empty product icon",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.empty_product),
            style = MaterialTheme.typography.h5
        )
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    LabisTheme {
        Surface {
            ProductListScreen(
                arrayListOf(
                    ProductDto(
                        id = 0,
                        name = "Product 1",
                        capital = 1000,
                        stock = 10,
                        storeId = 1,
                        description = "Lorem ipsum dolor amet",
                        priceToSell = 2000,
                        categoryId = 0
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun ProductListScreenEmptyPreview() {
    LabisTheme {
        Surface {
            ProductListScreen()
        }
    }
}