package com.pdm.firebasestoragedatabase.feature.presentation.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.pdm.firebasestoragedatabase.feature.presentation.dialogs.DialogProgress

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
        val keyBoarding = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoarding.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}