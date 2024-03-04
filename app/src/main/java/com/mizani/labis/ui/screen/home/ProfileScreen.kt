package com.mizani.labis.ui.screen.home

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import com.canhub.cropper.*
import com.mizani.labis.R
import com.mizani.labis.utils.DialogUtils

@Composable
fun ProfileScreen() {

    var name = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    var phoneNumber = rememberSaveable { mutableStateOf("") }

    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
            .verticalScroll(scroll),
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage()
            Spacer(modifier = Modifier.height(20.dp))
            InfoSection(
                name,
                email,
                phoneNumber
            )
            Spacer(modifier = Modifier.height(20.dp))
            AppInfoSection()
            Spacer(modifier = Modifier
                .height(20.dp))
            Text(text = "Version")
            Spacer(modifier = Modifier
                .fillMaxSize()
                .defaultMinSize(minHeight = 50.dp)
                .weight(1f))
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomEnd),
            colors = ButtonDefaults.buttonColors(Color.Red),
            shape = RoundedCornerShape(0)
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }
}

@Composable
fun ProfileImage() {

    val context = LocalContext.current
    val imageUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val cropImage = rememberLauncherForActivityResult(
        CropImageContract()
    ) {
        if (it.isSuccessful) {
            imageUri.value = it.uriContent
        }
    }

    val requestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val intent = Intent(context, CropImageActivity::class.java)
            val cropOptions = CropImageContractOptions(intent.data, CropImageOptions())
            cropOptions.cropImageOptions.fixAspectRatio = true
            cropImage.launch(cropOptions)
        } else {
            DialogUtils.rationaleDialogShow(context) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (image, icon) = createRefs()
        val modifier = Modifier
            .width(80.dp)
            .height(80.dp)
            .constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .clip(CircleShape)
            .border(width = 3.dp, color = Color.Gray, shape = RoundedCornerShape(80.dp))
        if (imageUri.value == null) {
            Image(
                painter = painterResource(id = R.drawable.otp),
                contentDescription = "",
                modifier = modifier
            )
        } else {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri.value)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "",
                modifier = modifier
            )
        }

        Icon(
            Icons.Default.PhotoCamera,
            contentDescription = "",
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(image.top)
                    bottom.linkTo(image.top)
                    end.linkTo(image.end)
                    start.linkTo(image.end)
                }
                .clickable {
                    requestPermission.launch(Manifest.permission.CAMERA)
                }
        )
    }

}

@Composable
private fun InfoSection(
    name: MutableState<String>,
    email: MutableState<String>,
    phoneNumber: MutableState<String>
) {

    var isEditEnabled = rememberSaveable  { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(4.dp, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = if (isEditEnabled.value) {
                    "Batal"
                } else {
                    "Ubah"
                },
                color = if (isEditEnabled.value) {
                    Color.Red
                 } else {
                    Color("#2F58CD".toColorInt())
                },
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        isEditEnabled.value = isEditEnabled.value.not()
                    }
            )
            ProfileTextField(text = name, placeholder = "Nama", isEditEnabled)
            Spacer(modifier = Modifier.height(10.dp))
            ProfileTextField(text = email, placeholder = "Email", isEditEnabled)
            Spacer(modifier = Modifier.height(10.dp))
            ProfileTextField(text = phoneNumber, placeholder = "No. HP", isEditEnabled)
        }
    }
}

@Composable
private fun AppInfoSection() {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(4.dp, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ProfileMenu(label = "Ubah Password")
            Spacer(modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(0.5.dp))
            ProfileMenu(label = "Kontak Kami")
            Spacer(modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(0.5.dp))
            ProfileMenu(label = "Syarat dan Ketentuan")
        }
    }
}

@Composable
private fun ProfileTextField(
    text: MutableState<String>,
    placeholder: String,
    isEnabled: MutableState<Boolean> = mutableStateOf(false)
) {
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = placeholder)
        },
        enabled = isEnabled.value,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

@Composable
private fun ProfileMenu(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            Icons.Default.ChevronRight, contentDescription = ""
        )
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}