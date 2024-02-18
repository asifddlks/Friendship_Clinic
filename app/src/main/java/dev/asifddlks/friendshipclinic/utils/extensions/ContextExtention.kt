package dev.asifddlks.bhaibhaiclinicApp.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import timber.log.Timber

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showLog(msg: String) {
    Timber.tag(this.javaClass.simpleName).d(msg)
}

fun Context.showErrorLog(msg: String) {
    Timber.tag(this.javaClass.simpleName).e(msg)
}

fun <T> Context.nextActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

