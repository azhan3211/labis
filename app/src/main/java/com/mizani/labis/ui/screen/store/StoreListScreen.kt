package com.mizani.labis.ui.screen.store

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Store
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.ui.component.SearchComponent
import com.mizani.labis.ui.component.StoreItemComponent
import com.mizani.labis.ui.component.TopBarComponent

@Composable
fun StoreListScreen(
    stores: List<StoreDto> = arrayListOf(),
    navigateToCreateStoreScreen: (() -> Unit)? = null,
    navigateToDetailStoreScreen: ((StoreDto) -> Unit)? = null,
    onSelectStoreListener: ((StoreDto) -> Unit)? = null,
    onDeleteListener: ((StoreDto) -> Unit)? = null,
    backListener: (() -> Unit)? = null
) {

    var searchKeyword = rememberSaveable { mutableStateOf("") }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.background(Color.White)
            ) {
                TopBarComponent(title = stringResource(id = R.string.your_store)) {
                    backListener?.invoke()
                }

                if (stores.isEmpty()) {
                    EmptyStoreSection()
                } else {
                    Box(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        SearchComponent(
                            value = searchKeyword,
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn {
                            itemsIndexed(stores) { index, item ->
                                if (index == 0) {
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                                StoreItemComponent(
                                    item,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp)
                                        .shadow(2.dp, RoundedCornerShape(10.dp)),
                                    onDetailListener = {
                                        navigateToDetailStoreScreen?.invoke(it)
                                    },
                                    onDeleteListener = {
                                        onDeleteListener?.invoke(it)
                                    },
                                    onSelectListener = {
                                        onSelectStoreListener?.invoke(it)
                                    }
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                backgroundColor = Color.Blue,
                onClick = {
                    navigateToCreateStoreScreen?.invoke()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-20).dp, (-30).dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add store",
                    tint = Color.White
                )
            }
        }
    }

}

@Composable
internal fun EmptyStoreSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.Store,
            contentDescription = "Empty store icon",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.empty_store),
            style = MaterialTheme.typography.h5
        )
    }
}

@Preview
@Composable
private fun StoreListScreenPreview() {
    StoreListScreen(
        arrayListOf(
            StoreDto(
                id = 0,
                name = "Store 1",
                address = "Address 1"
            )
        )
    )
}

@Preview
@Composable
private fun StoreListScreenEmptyPreview() {
    StoreListScreen(
        arrayListOf()
    )
}

@Preview
@Composable
private fun EmptyStoreSectionPreview() {
    Surface {
        EmptyStoreSection()
    }
}