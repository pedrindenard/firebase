package com.pdm.firebase.feature.presentation.base

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.pdm.firebase.R
import com.pdm.firebase.feature.presentation.dialog.DialogProgress
import com.pdm.firebase.util.BLACK
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.dpToPx
import java.lang.Exception


abstract class BaseFragment : Fragment() {

    private var progressDialog: DialogProgress? = null

    fun showProgressDialog() {
        activity?.let {
            if (progressDialog == null) {
                progressDialog = DialogProgress()
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

    fun hideKeyboard() {
        val keyBoarding =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoarding.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun snackBar(description: String, color: String) {
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
                params.topMargin = context.dpToPx(32F)
                params.gravity = Gravity.TOP
            } catch (e: Exception) {
                params as CoordinatorLayout.LayoutParams
                params.topMargin = context.dpToPx(32F)
                params.gravity = Gravity.TOP
            }

            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            view.layoutParams = params
            this.show()
        }
    }
}