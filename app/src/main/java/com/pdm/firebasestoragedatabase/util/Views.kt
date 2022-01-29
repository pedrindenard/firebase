package com.pdm.firebasestoragedatabase.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.pdm.firebasestoragedatabase.R
import com.pdm.firebasestoragedatabase.feature.presentation.dialogs.DialogAlert
import com.pdm.firebasestoragedatabase.feature.presentation.dialogs.DialogProgress

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

fun Activity?.makeToast(description: String) {
    Toast.makeText(
        this, description,
        Toast.LENGTH_SHORT
    ).show()
}