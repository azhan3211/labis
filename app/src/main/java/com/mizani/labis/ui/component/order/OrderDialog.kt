package com.mizani.labis.ui.component.order

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mizani.labis.R
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.ui.component.textfield.OutlinedTextFieldComponent
import com.mizani.labis.utils.StringUtils.toCurrency
import java.text.DecimalFormat

@Composable
fun OrderDialog(
    totalPrice: Int = 0,
    onSave: (String) -> Unit = {},
    setShowDialog: () -> Unit = {}
) {

    var moneyPaid by rememberSaveable {
        mutableStateOf("0")
    }

    var moneyReturn by rememberSaveable {
        mutableStateOf("0")
    }

    var isPayLater by rememberSaveable {
        mutableStateOf(false)
    }

    var name by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(moneyPaid) {
        moneyReturn = getReturnMoney(moneyPaid, totalPrice.toString())
    }

    val context = LocalContext.current

    Dialog(onDismissRequest = { setShowDialog.invoke() }) {
        Surface {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.paid),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "${stringResource(id = R.string.total_price)} ${totalPrice.toCurrency()}")
                }
                OutlinedTextFieldComponent(
                    value = moneyPaid,
                    label = stringResource(id = R.string.money_paid),
                    keyboardType = KeyboardType.Number,
                    onChange = {
                        moneyPaid = it
                    }
                )
                OutlinedTextFieldComponent(
                    value = moneyReturn,
                    label = stringResource(id = R.string.money_return),
                    keyboardType = KeyboardType.Number,
                    isEnabled = false,
                    onChange = {
                        moneyReturn = it
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = isPayLater,
                        onCheckedChange = {
                            name = ""
                            isPayLater = it
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.pay_later)
                    )
                }
                if (isPayLater) {
                    OutlinedTextFieldComponent(
                        value = name,
                        label = stringResource(id = R.string.name),
                        keyboardType = KeyboardType.Text,
                        onChange = {
                            name = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    ButtonComponent(
                        label = stringResource(id = R.string.cancel),
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.weight(1f),
                        callback = setShowDialog
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    ButtonComponent(
                        label = stringResource(id = R.string.save),
                        modifier = Modifier.weight(1f),
                        callback = {
                            if (isPayLater && name.isNullOrEmpty()) {
                                Toast.makeText(context, context.getString(R.string.paid_name_error), LENGTH_SHORT).show()
                                return@ButtonComponent
                            }
                            onSave.invoke(name)
                        }
                    )
                }
            }
        }
    }
}

private fun getReturnMoney(paid: String, totalPrice: String): String {
    val decimalFormat = DecimalFormat("###,###,###")
    val sellInt = paid.replace(".", "").toInt()
    val priceInt = totalPrice.replace(".", "").toInt()
    val result = sellInt - priceInt
    return decimalFormat.format(result).replace(",", ".")
}


@Preview
@Composable
private fun OrderDialogPreview() {
    Surface {
        OrderDialog()
    }
}