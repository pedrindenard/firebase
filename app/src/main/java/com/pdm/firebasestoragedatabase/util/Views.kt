package com.pdm.firebasestoragedatabase.util

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.pdm.firebasestoragedatabase.ui.dialogs.DialogAlert
import com.pdm.firebasestoragedatabase.ui.dialogs.DialogProgress

fun View.setOnSingleClickListener(debounceTime: Long = 600, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun handlerDelay(delayMillis: Long = 1000, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action()
    }, delayMillis)
}

fun FragmentActivity.showDialogAlert(title: String, description: String) {
    DialogAlert(
        title = title,
        description = description
    ).show(
        this@showDialogAlert.supportFragmentManager,
        "AlertDialog"
    )
}

fun FragmentActivity.showDialogProgress() {
    DialogProgress().show(
        this@showDialogProgress.supportFragmentManager,
        "ProgressDialog"
    )
}