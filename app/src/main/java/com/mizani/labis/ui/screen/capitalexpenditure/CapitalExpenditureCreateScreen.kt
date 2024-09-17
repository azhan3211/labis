package com.mizani.labis.ui.screen.capitalexpenditure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.CapitalExpenditureCreateDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDto
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.textfield.TextFieldComponent
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import com.mizani.labis.utils.StringUtils.toCurrency
import java.util.Date

@Composable
fun CapitalExpenditureCreateScreen(
    onBackListener: () -> Unit = {},
    onAddCapitalExpenditure: (List<CapitalExpenditureCreateDto>) -> Unit
) {

    val expenditures = remember {
        mutableStateListOf(
            CapitalExpenditureCreateDto(
                name = "",
                price = 0
            )
        )
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.create_capital_expenditure),
                callback = onBackListener
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    itemsIndexed(expenditures) { index, item ->
                        ExpenditureListItem(
                            capitalExpenditureCreateDto = item,
                            onChange = { date ->
                                expenditures[index] = date
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .imePadding()
                        .windowInsetsPadding(WindowInsets.ime)
                ) {
                    ButtonComponent(
                        modifier = Modifier.weight(1f),
                        label = stringResource(id = R.string.add),
                        color = MaterialTheme.colors.secondary,
                        callback = {
                            expenditures.add(CapitalExpenditureCreateDto())
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    ButtonComponent(
                        modifier = Modifier.weight(1f),
                        label = stringResource(id = R.string.save),
                        callback = {
                            onAddCapitalExpenditure.invoke(expenditures.toList())
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

@Composable
private fun TotalExpenditureAndDateSection(totalExpenditure: Int, date: Date = Date()) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.date),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = date.toReadableView(),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.total_capital_expenditure),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = totalExpenditure.toCurrency(),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ExpenditureListItem(
    capitalExpenditureCreateDto: CapitalExpenditureCreateDto,
    onChange: (CapitalExpenditureCreateDto) -> Unit
) {

    val name = rememberSaveable { mutableStateOf(capitalExpenditureCreateDto.name) }
    val price = rememberSaveable { mutableStateOf(capitalExpenditureCreateDto.price.toString()) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = name.value,
            label = stringResource(id = R.string.name),
            onChange = { text ->
                name.value = text
                onChange.invoke(
                    CapitalExpenditureCreateDto(
                        name = text,
                        price = price.value.replace(".", "").toInt()
                    )
                )
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        TextFieldComponent(
            modifier = Modifier
                .width(120.dp),
            text = price.value,
            label = "${stringResource(id = R.string.price)} Rp",
            keyboardType = KeyboardType.Number,
            onChange = { text ->
                price.value = text
                onChange.invoke(
                    CapitalExpenditureCreateDto(
                        name = name.value,
                        price = text.replace(".", "").toInt()
                    )
                )
            }
        )
    }
}

@Preview
@Composable
private fun CapitalExpenditureCreateScreenPreview() {
    Surface {
        CapitalExpenditureCreateScreen(
            onAddCapitalExpenditure = {}
        )
    }
}