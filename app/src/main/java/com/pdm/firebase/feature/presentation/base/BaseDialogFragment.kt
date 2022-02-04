package com.pdm.firebase.feature.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.pdm.firebase.R
import com.pdm.firebase.util.BLACK
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.dpToPx
import java.lang.Exception

abstract class BaseDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window!!.setGravity(Gravity.BOTTOM)
        dialog?.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
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