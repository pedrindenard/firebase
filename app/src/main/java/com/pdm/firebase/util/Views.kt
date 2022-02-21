package com.pdm.firebase.util

import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import com.pdm.firebase.R

fun AppCompatToggleButton.toggle(editText: TextInputEditText) {
    this.setOnClickListener {
        if (this.isChecked) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.length())
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.length())
        }
    }
}

fun ViewGroup?.addViews(requireContext: Context, description: List<String>?) {
    description?.forEach {
        this?.addView(
            TextView(requireContext).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 0F)
                    )
                    setPadding(
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 0F),
                        requireContext.dpToPx(dp = 16F)
                    )
                }
                text = it
                textAlignment = left
                typeface = ResourcesCompat.getFont(requireContext, R.font.stellar_regular)
            }
        )
    }
}

fun View?.disableIt() {
    this?.isEnabled = false
    this?.isClickable = false
    this?.isActivated = false
}

fun View.handler(isEnabled: Boolean) {
    this.background.alpha = when (isEnabled) {
        true -> 255
        false -> 185
    }
    this.isEnabled = isEnabled
}

fun SwipeRefreshLayout.setSwipeRefresh(onSwipe: () -> Unit) {
    setColorSchemeColors(ContextCompat.getColor(context, R.color.yellow_dark))
    setProgressBackgroundColorSchemeColor(Color.BLACK)
    setOnRefreshListener { onSwipe() }
}

fun SwipeRefreshLayout.stopSwipe() {
    if (this.isRefreshing) this.isRefreshing = false
}

fun TextInputEditText.addListenerSearch(
    listener: Boolean? = true,
    beforeTextChanged: ((CharSequence?) -> Unit)? = null,
    onTextChanged: ((CharSequence?) -> Unit)? = null,
    onTextWrite: ((CharSequence?) -> Unit)? = null,
    afterTextChanged: ((Editable?) -> Unit)? = null
) {
    var enableListenerAgain: Boolean = listener!!
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (beforeTextChanged != null && enableListenerAgain) {
                beforeTextChanged(s)
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (onTextChanged != null && enableListenerAgain) {
                onTextChanged(s)
            }
            if (onTextWrite != null && enableListenerAgain) {
                onTextWrite(s)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (afterTextChanged != null && enableListenerAgain) {
                afterTextChanged(s)
            } else {
                enableListenerAgain = true
            }
        }
    })
}