package com.mizani.labis.utils

import android.app.AlertDialog
import android.content.Context
import com.mizani.labis.R

object DialogUtils {
    fun rationaleDialogShow(context: Context, message: String? = null, callback: () -> Unit = {}) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.dialog_permission_title))
            .setMessage(message ?: context.getString(R.string.dialog_permission_default_message))
            .setCancelable(true)
            .setPositiveButton(context.getString(R.string.dialog_permission_button_positive)) { _, _ ->
                callback.invoke()
            }
            .show()
    }

    fun confirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = context.getString(R.string.ok),
        dismissListener: (() -> Unit)? = null,
        okListener: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setOnDismissListener {
                dismissListener?.invoke()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { _, _ ->

            }
            .setPositiveButton(positiveButtonText) { _, _ ->
                okListener?.invoke()
            }
            .show()
    }
}