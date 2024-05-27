package com.mizani.labis.ui.component.product

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.mizani.labis.R
import com.mizani.labis.ui.component.ButtonComponent
import com.mizani.labis.utils.DialogUtils

@Composable
fun UploadImageComponent() {
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

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(160.dp)
                .height(160.dp),
        ) {
            if (imageUri.value == null) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Product image",
                    tint = Color.Gray
                )
            } else {
                val bitmap: Bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri.value)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Product image preview",
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        ButtonComponent(
            label = stringResource(id = R.string.upload_image),
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
        ) {
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }
}