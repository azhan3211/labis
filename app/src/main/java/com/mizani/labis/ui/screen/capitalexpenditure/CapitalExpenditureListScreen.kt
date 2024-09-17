package com.mizani.labis.ui.screen.capitalexpenditure

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mizani.labis.R
import com.mizani.labis.domain.model.dto.CapitalExpenditureDataDto
import com.mizani.labis.ui.component.TopBarComponent
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import com.mizani.labis.utils.StringUtils.toCurrency
import java.util.Date

@Composable
fun CapitalExpenditureListScreen(
    date: Date = Date(),
    totalExpenditure: Int = 0,
    capitalExpenditureDataDtos: List<CapitalExpenditureDataDto> = listOf(),
    onBackListener: () -> Unit,
    onAddListener: () -> Unit,
    onEditListener: (CapitalExpenditureDataDto) -> Unit,
    onDeleteListener: (CapitalExpenditureDataDto) -> Unit
) {

    BackHandler {
        onBackListener.invoke()
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.capital_expenditure),
                callback = onBackListener
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                backgroundColor = Color.Blue,
                onClick = {
                    onAddListener.invoke()
                },
                modifier = Modifier
                    .offset((-20).dp, (-30).dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add product icon",
                    tint = Color.White
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                TotalExpenditureAndDateSection(totalExpenditure = totalExpenditure, date = date)
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn() {
                    itemsIndexed(capitalExpenditureDataDtos) { index, item ->
                        ExpenditureListItem(
                            capitalExpenditureDto = item,
                            onDeleteListener = onDeleteListener,
                            onEditListener = onEditListener
                        )
                    }
                }
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
    capitalExpenditureDto: CapitalExpenditureDataDto,
    onDeleteListener: (CapitalExpenditureDataDto) -> Unit = {},
    onEditListener: (CapitalExpenditureDataDto) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)
       ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
           ) {
               Text(
                   modifier = Modifier
                       .fillMaxWidth()
                       .weight(1f),
                   text = capitalExpenditureDto.date.toReadableView()
               )
               Text(text = capitalExpenditureDto.price.toCurrency())
               Spacer(modifier = Modifier.width(10.dp))
               Icon(
                   Icons.Outlined.Delete,
                   contentDescription = "",
                   modifier = Modifier.clickable {
                       onDeleteListener.invoke(capitalExpenditureDto)
                   }
               )
               Spacer(modifier = Modifier.width(5.dp))
               Icon(
                   Icons.Outlined.Edit,
                   contentDescription = "",
                   modifier = Modifier.clickable {
                       onEditListener.invoke(capitalExpenditureDto)
                   }
               )
           }
           Spacer(modifier = Modifier.height(20.dp))
           Text(text = capitalExpenditureDto.description)
       }
    }
}

@Preview
@Composable
private fun CapitalExpenditureListScreenPreview() {
    Surface {
        CapitalExpenditureListScreen(
            capitalExpenditureDataDtos = listOf(CapitalExpenditureDataDto(description = "Test Rp.100.000\nTest2 Rp.200.000")),
            onEditListener = {},
            onDeleteListener = {},
            onBackListener = {},
            onAddListener = {}
        )
    }
}