package com.mizani.labis.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun ReportScreen() {

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Color("#2F58CD".toColorInt()))
            .verticalScroll(scroll),
    ) {
        BenefitLostSection()
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
                DateSection()
                StatisticSection()
                ButtonSection()
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun BenefitLostSection() {
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
                    text = "Keuntungan",
                    color = Color.White
                )
                Text(
                    text = "Rp.1.000.000",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@Composable
private fun DateSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Catatan Keuangan Hari ini",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
        Card(
            modifier = Modifier
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 5.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
            ) {
                Text(
                    text = "24 Mei 2023"
                )
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

@Composable()
private fun StatisticSection() {
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu("Total Penjualan", "Rp.999.999")
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu("Sisa Modal", "Rp.999.999")
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu("Pengeluaran", "Rp.200.000")
    Spacer(modifier = Modifier.height(20.dp))
    ReportMenu("Hutang", "Rp.300.000")
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
private fun ButtonSection() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text = "Lihat Barang Terjual",
            color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(Color("#2F58CD".toColorInt())),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Lihat Catatan Perbulan",
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
private fun ReportMenu(label: String, price: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(2.dp, RoundedCornerShape(10.dp)),
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
                text = price,
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
    ReportScreen()
}