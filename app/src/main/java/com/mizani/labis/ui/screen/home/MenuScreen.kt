package com.mizani.labis.ui.screen.home

import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.ChipItemComponent
import com.mizani.labis.ui.component.SearchComponent
import com.mizani.labis.ui.component.menu.MenuItemComponent
import com.mizani.labis.ui.component.order.OrderTotalPriceComponent

@Composable
fun MenuScreen(
    selectedStore: StoreDto = StoreDto(),
    categories: List<ProductCategoryDto> = listOf(),
    products: List<ProductDto> = listOf(),
    totalPrice: Int = 0,
    onStoreClicked: (() -> Unit)? = null,
    onInc: (ProductDto) -> Unit = {},
    onDec: (ProductDto) -> Unit = {},
    onOrderClicked: () -> Unit = {}
) {

    val searchKeyword = rememberSaveable { mutableStateOf("") }
    val selectedCategoryIndex = rememberSaveable { mutableStateOf(0) }
    var isAnimated by rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(targetState = isAnimated, label = "transition")
    val orderTotalOffset by transition.animateOffset(label = "order total offset") {
        if (it) {
            Offset(0f, -10f)
        } else {
            Offset(0f, 100f)
        }
    }

    LaunchedEffect(totalPrice) {
        isAnimated = totalPrice > 0
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                StoreSection(
                    storeDto = selectedStore,
                    onStoreClicked = onStoreClicked
                )
                Spacer(modifier = Modifier.height(20.dp))
                SearchSection(
                    searchKeyword = searchKeyword.value,
                    onChange = {
                        searchKeyword.value = it
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                CategorySection(
                    categories = categories,
                    selectedCategoryIndex = selectedCategoryIndex.value,
                    onSelectedIndexChanged = {
                        selectedCategoryIndex.value = it
                    }
                )
                Content(
                    storeDto = selectedStore,
                    products = products,
                    searchKeyword = searchKeyword.value,
                    onInc = onInc,
                    onDec = onDec,
                    onStoreClicked = onStoreClicked,

                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 16.dp)
                    .offset(orderTotalOffset.x.dp, orderTotalOffset.y.dp)
            ) {
                OrderTotalPriceComponent(
                    totalPrice = totalPrice,
                    onClick = onOrderClicked
                )
            }
        }
    }

}

@Composable
private fun Content(
    storeDto: StoreDto,
    products: List<ProductDto> = listOf(),
    searchKeyword: String = "",
    onInc: (ProductDto) -> Unit,
    onDec: (ProductDto) -> Unit,
    onStoreClicked: (() -> Unit)? = null
) {
    if (storeDto.id == 0L) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.width(180.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                text = stringResource(id = R.string.no_store_selected)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                modifier = Modifier.width(180.dp),
                label = stringResource(id = R.string.select_store),
                callback = {
                    onStoreClicked?.invoke()
                }
            )
        }
    } else {
        MenuListSection(
            products = products,
            onInc = onInc,
            onDec = onDec,
            searchKeyword = searchKeyword
        )
    }
}

@Composable
private fun MenuListSection(
    products: List<ProductDto> = listOf(),
    searchKeyword: String = "",
    onInc: (ProductDto) -> Unit = {},
    onDec: (ProductDto) -> Unit = {},
) {

    Column {
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            itemsIndexed(products) { index, item ->
                if (item.name.lowercase().contains(searchKeyword.lowercase()) || item.name.isEmpty()) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        MenuItemComponent(
                            productDto = item,
                            onInc = {
                                onInc.invoke(item)
                            },
                            onDec = {
                                onDec.invoke(item)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (index == products.size - 1) {
                        Spacer(modifier = Modifier.height(180.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchSection(
    searchKeyword: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SearchComponent(
            value = searchKeyword,
            onChange = onChange
        )
    }
}

@Composable
private fun CategorySection(
    categories: List<ProductCategoryDto>,
    selectedCategoryIndex: Int,
    onSelectedIndexChanged: ((Int) -> Unit) = {}
) {
    LazyRow {
        itemsIndexed(categories) { index, item ->
            if (index == 0) {
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
            }
            ChipItemComponent(
                text = item.name,
                index = index,
                isSelected = selectedCategoryIndex == index
            ) { chipIndex ->
                onSelectedIndexChanged.invoke(chipIndex)
            }
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
        }
    }
}

@Composable
private fun StoreSection(
    storeDto: StoreDto,
    onStoreClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = storeDto.name,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.Store,
            modifier = Modifier.clickable {
                onStoreClicked?.invoke()
            },
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun PreviewMenu() {
    MenuScreen()
}