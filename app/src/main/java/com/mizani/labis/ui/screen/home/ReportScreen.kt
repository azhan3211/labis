package com.mizani.labis.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.mizani.labis.R
import com.mizani.labis.ui.screen.report.ReportActivity
import com.mizani.labis.utils.LabisDateUtils
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import com.mizani.labis.utils.StringUtils.toCurrency
import java.util.Date

@Composable
fun ReportScreen(
    totalSalePaid: Int = 0,
    totalSaleUnpaid: Int = 0,
    profit: Int = 0,
    capital: Int = 0,
    onAvailableCapitalClicked: () -> Unit,
    onReportClicked: (String) -> Unit = {},
    onDateChange: (Date, Date) -> Unit = { _, _ -> }
) {

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .verticalScroll(scroll),
    ) {
        BenefitLostSection(profit)
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 40.dp),
            ) {
                DateSection(
                    onDateChange = onDateChange
                )
                StatisticSection(
                    totalSalePaid,
                    totalSaleUnpaid,
                    capital,
                    onAvailableCapitalClicked = onAvailableCapitalClicked,
                    onClick = {
                        onReportClicked.invoke(it)
                    }
                )
                ButtonSection(
                    onReportClicked = onReportClicked
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun BenefitLostSection(
    profit: Int
) {
    Box(
        modifier = Modifier.aspectRatio(3f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.profit),
                    color = Color.White
                )
                Text(
                    text = profit.toCurrency(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateSection(
    onDateChange: (Date, Date) -> Unit = { _, _ -> }
) {

    val datePickerState = rememberDatePickerState()
    val isDialogShown = rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (isDialogShown.value) {
                DatePickerDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        Button(
                            onClick = {
                                isDialogShown.value = false
                                onDateChange.invoke(
                                    Date(
                                        datePickerState.selectedDateMillis ?: Date().time
                                    ),
                                    Date(
                                        datePickerState.selectedDateMillis ?: Date().time,
                                    )
                                )
                            }
                        ) {
                            Text(text = stringResource(id = R.string.ok))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { isDialogShown.value = false }
                        ) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 5.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                    .clickable {
                        isDialogShown.value = true
                    }
            ) {
                if (datePickerState.selectedDateMillis == datePickerState.selectedDateMillis) {
                    Text(
                        text = datePickerState.selectedDateMillis?.toReadableView()
                            ?: LabisDateUtils.getCurrentTime().toReadableView()
                    )
                } else {
                    Text(
                        text = "${datePickerState.selectedDateMillis?.toReadableView()} - ${datePickerState.selectedDateMillis?.toReadableView()}"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                        .height(5.dp)
                )
                Icon(
                    Icons.Default.ArrowDropDown, contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun StatisticSection(
    totalSalePaid: Int,
    totalSaleUnpaid: Int,
    capital: Int,
    onAvailableCapitalClicked: () -> Unit,
    onClick: (String) -> Unit = {}
) {
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu(
        stringResource(id = R.string.omzet),
        totalSalePaid,
        onClick = {
            onClick.invoke(ReportActivity.REPORT_ALL)
        }
    )
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu(
        stringResource(id = R.string.available_capital),
        capital,
        onClick = onAvailableCapitalClicked
    )
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu(stringResource(id = R.string.capital_expenditure), 0)
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu(
        stringResource(id = R.string.debt),
        totalSaleUnpaid,
        onClick = {
            onClick.invoke(ReportActivity.REPORT_UNPAID)
        }
    )
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
private fun ButtonSection(
    onReportClicked: (String) -> Unit = {}
) {
    Button(
        onClick = {
            onReportClicked.invoke(ReportActivity.REPORT_PAID)
        },
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = stringResource(id = R.string.see_sold_product),
            color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            onReportClicked.invoke(ReportActivity.REPORT_MONTHLY)
        },
        colors = ButtonDefaults.buttonColors(Color("#2F58CD".toColorInt())),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.see_monthly_report),
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
            Icon(
                Icons.Default.PointOfSale,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun ReportMenu(
    label: String,
    price: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(2.dp, RoundedCornerShape(10.dp))
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = price.toCurrency(),
                fontSize = 18.sp
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
private fun PreviewReportScreen() {
    ReportScreen(
        onAvailableCapitalClicked = {}
    )
}