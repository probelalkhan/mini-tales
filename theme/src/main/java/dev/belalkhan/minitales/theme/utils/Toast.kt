package dev.belalkhan.minitales.theme.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(message: String, onToastDisplayChange: (Boolean) -> Unit) {
    showToast(message, onToastDisplayChange)
}

fun Context.toast(@StringRes message: Int, onToastDisplayChange: (Boolean) -> Unit) {
    showToast(getString(message), onToastDisplayChange)
}

private fun Context.showToast(message: String, onToastDisplayChange: (Boolean) -> Unit) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).also {
        it.addCallback(object : Toast.Callback() {
            override fun onToastHidden() {
                super.onToastHidden()
                onToastDisplayChange(false)
            }

            override fun onToastShown() {
                super.onToastShown()
                onToastDisplayChange(true)
            }
        })
    }.show()
}
