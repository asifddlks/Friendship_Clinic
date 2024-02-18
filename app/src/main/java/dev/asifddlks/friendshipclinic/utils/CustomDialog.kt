package dev.asifddlks.friendshipclinic.utils

import android.app.Dialog
import android.content.Context
import dev.asifddlks.friendshipclinic.R

class CustomDialog(val context: Context) {

    private var dialog: Dialog = Dialog(context)

    fun progressDialog(): Dialog {

        dialog.setContentView(R.layout.dialog_progress)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setCancelable(false)
        return dialog
    }

}