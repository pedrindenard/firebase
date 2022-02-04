package com.pdm.firebase.feature.presentation.base

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.pdm.firebase.R
import com.pdm.firebase.feature.presentation.dialog.ProgressDialog
import com.pdm.firebase.util.BLACK
import com.pdm.firebase.util.RED
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.textfield.TextInputEditText


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
            val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
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
}