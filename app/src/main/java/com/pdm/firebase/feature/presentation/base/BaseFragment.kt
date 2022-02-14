package com.pdm.firebase.feature.presentation.base

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pdm.firebase.R
import com.pdm.firebase.feature.presentation.dialog.ProgressDialog
import com.pdm.firebase.util.BLACK
import com.pdm.firebase.util.RED

abstract class BaseFragment : Fragment() {

    private var progressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        activity?.let {
            if (progressDialog == null) {
                progressDialog = ProgressDialog()
                progressDialog?.show(it.supportFragmentManager, "ProgressDialog")
            }
        }
    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    fun TextInputEditText.showKeyBoard() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

        val keyboard = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        keyboard!!.showSoftInput(this, 0)
    }

    fun hideKeyboard() {
        val keyBoarding = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoarding.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun TextInputLayout.setErrorInput(
        textInputEditText: TextInputEditText,
        appCompatTextView: AppCompatTextView? = null,
        message: String? = ""
    ) {
        val errorColor = ContextCompat.getColor(context, R.color.red)
        val grayColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.yellow_dark
            )
        )
        val blackColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.yellow_dark
            )
        )
        val errorStateColor = ColorStateList.valueOf(errorColor)

        this.defaultHintTextColor = errorStateColor
        this.setBoxStrokeColorStateList(
            AppCompatResources.getColorStateList(
                context,
                R.color.color_state_edit_text_error
            )
        )

        activity?.currentFocus?.clearFocus()
        appCompatTextView?.text = message ?: ""
        textInputEditText.setTextColor(errorColor)
        textInputEditText.clearFocus()

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (textInputEditText.length() > 0) {
                    if (this@setErrorInput.boxStrokeColor == errorColor) {
                        this@setErrorInput.setBoxStrokeColorStateList(
                            AppCompatResources.getColorStateList(
                                context,
                                R.color.color_state_edit_text
                            )
                        )
                        this@setErrorInput.boxStrokeColor = ContextCompat.getColor(
                            context,
                            R.color.yellow_dark
                        )
                        this@setErrorInput.defaultHintTextColor = blackColor
                        this@setErrorInput.hintTextColor = grayColor
                    } else if (textInputEditText.length() > 0) {
                        this@setErrorInput.defaultHintTextColor = grayColor
                    }
                    textInputEditText.setTextColor(blackColor)
                    appCompatTextView?.text = ""
                } else {
                    this@setErrorInput.defaultHintTextColor = blackColor
                    this@setErrorInput.hintTextColor = grayColor
                }
            }
        })
    }

    fun TextInputLayout.defaultStateColor() {
        this.setBoxStrokeColorStateList(
            AppCompatResources.getColorStateList(
                context,
                R.color.color_state_edit_text
            )
        )
        this.boxStrokeColor = ContextCompat.getColor(
            context,
            R.color.yellow_dark
        )
    }

    fun showSnackBar(description: String, color: String) {
        Snackbar.make(requireView(), description, Snackbar.LENGTH_LONG).apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    when (color) {
                        BLACK -> {
                            R.color.black
                        }
                        RED -> {
                            R.color.red
                        }
                        else -> {
                            R.color.gray
                        }
                    }
                )
            )
            val view = this.view
            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            val params = view.layoutParams

            try {
                params as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
            } catch (e: Exception) {
                params as CoordinatorLayout.LayoutParams
                params.gravity = Gravity.TOP
            }

            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            view.layoutParams = params
            this.show()
        }
    }

    companion object {
        private const val EXCEPTION = "Binding only is valid after onCreateView"
    }
}