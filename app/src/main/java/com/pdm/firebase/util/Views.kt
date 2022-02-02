package com.pdm.firebase.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pdm.firebase.R

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

fun View.setMargin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun AppBarLayout.initCollapsingToolbar() {
    var scrollRange = -1

    addOnOffsetChangedListener(
        AppBarLayout.OnOffsetChangedListener { barLayout, _ ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
        }
    )
}

fun Activity.setErrorInput(
    textInputEditText: TextInputEditText,
    textInputLayout: TextInputLayout,
    appCompatTextView: AppCompatTextView? = null,
    message: String?
) {
    val errorColor = ContextCompat.getColor(this, R.color.red)
    val grayColor = ColorStateList.valueOf(
        ContextCompat.getColor(
            this,
            R.color.gray
        )
    )
    val blackColor = ColorStateList.valueOf(
        ContextCompat.getColor(
            this,
            R.color.black
        )
    )
    val errorStateColor = ColorStateList.valueOf(errorColor)

    textInputLayout.defaultHintTextColor = errorStateColor
    textInputLayout.setBoxStrokeColorStateList(
        AppCompatResources.getColorStateList(
            this,
            R.color.color_state_edit_text_error
        )
    )

    this.currentFocus?.clearFocus()
    appCompatTextView?.text = message ?: ""
    textInputEditText.setTextColor(errorColor)
    textInputEditText.clearFocus()

    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (textInputEditText.length() > 0) {
                if (textInputLayout.boxStrokeColor == errorColor) {
                    textInputLayout.setBoxStrokeColorStateList(
                        AppCompatResources.getColorStateList(
                            this@setErrorInput,
                            R.color.color_state_edit_text
                        )
                    )
                    textInputLayout.boxStrokeColor = ContextCompat.getColor(
                        this@setErrorInput,
                        R.color.gray
                    )
                    textInputLayout.defaultHintTextColor = blackColor
                    textInputLayout.hintTextColor = grayColor
                } else if (textInputEditText.length() > 0) {
                    textInputLayout.defaultHintTextColor = grayColor
                }
                textInputEditText.setTextColor(blackColor)
                appCompatTextView?.text = ""
            } else {
                textInputLayout.defaultHintTextColor = blackColor
                textInputLayout.hintTextColor = grayColor
            }
        }
    })
}

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

fun handlerDelay(delayMillis: Long = 1000, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action()
    }, delayMillis)
}

fun View?.disableIt() {
    this?.isEnabled = false
    this?.isClickable = false
    this?.isActivated = false
}