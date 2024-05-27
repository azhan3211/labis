package com.mizani.labis.ui.component.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.ProductCategoryDto

@Composable
fun CategoriesDropDownComponent(
    categories: List<ProductCategoryDto> = listOf(),
    selectedCategoryIndex: Int = 0,
    isAddCategory: Boolean = false,
    onCategorySelected: (Int) -> Unit = {}
) {
    val expanded = rememberSaveable { mutableStateOf(false) }

    if (isAddCategory.not()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = expanded.value.not()
                }
            ) {
                repeat(categories.size + 1) {
                    if (it == 0) {
                        DropdownMenuItem(
                            onClick = {
                                expanded.value = false
                                onCategorySelected.invoke(it)
                            }
                        ) {
                            Text(text = stringResource(id = R.string.select_category))
                        }
                    } else {
                        DropdownMenuItem(
                            onClick = {
                                expanded.value = false
                                onCategorySelected.invoke(it)
                            }
                        ) {
                            Text(text = categories[it - 1].name)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        expanded.value = expanded.value.not()
                    }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedCategoryIndex == 0) {
                        stringResource(id = R.string.select_category)
                    } else {
                        categories[selectedCategoryIndex - 1].name
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Product category dropdown arrow",
                    tint = Color.Black
                )
            }
        }
    }
}