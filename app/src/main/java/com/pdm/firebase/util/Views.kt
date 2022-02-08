package com.pdm.firebase.util

import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
    message: String? = ""
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

fun View.animateAlpha(delayMillis: Long? = null, isVisible: Boolean? = true) {
    when (isVisible) {
        true -> {
            animate().alpha(1F).duration = delayMillis ?: 500
        }
        else -> {
            animate().alpha(0F).duration = delayMillis ?: 500
        }
    }
}

fun View.rotateView(rotate: Boolean, speed: Long, angleN: Float, angleP: Float): Boolean {
    this.animate().setDuration(speed)
        .setListener(object : AnimatorListenerAdapter() {})
        .rotation(if (rotate) angleN else angleP)
    return rotate
}

fun View.expand(speed: Int) {
    this.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = this.measuredHeight
    this.layoutParams.height = 1
    this.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height = if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT
            else (targetHeight * interpolatedTime).toInt()
            this@expand.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((targetHeight / this.context.resources.displayMetrics.density).toInt().toLong() * speed)
    this.startAnimation(a)
}

fun View.collapse(speed: Int) {
    val initialHeight: Int = this.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                this@collapse.visibility = View.GONE
            } else {
                this@collapse.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapse.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((initialHeight / this.context.resources.displayMetrics.density).toInt().toLong() * speed)
    this.startAnimation(a)
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}