package com.pdm.firebase.feature.presentation.fragment.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.pdm.firebase.R
import com.pdm.firebase.databinding.DialogNumberPhoneBinding
import com.pdm.firebase.feature.presentation.base.BaseDialogFragment
import com.pdm.firebase.util.*

class NumberPhoneDialog : BaseDialogFragment() {

    private lateinit var mClickListener: ClickListener

    private var _binding: DialogNumberPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNumberPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlerDelay(delayMillis = 300) {
            mClickListener.showKeyBoard(
                textInputEditText = binding.numberField
            )
        }

        handlerClicksDialog()
    }

    private fun handlerClicksDialog() {
        binding.loginWithNumberPhone.setOnSingleClickListener {
            val number = binding.numberField.text.toString()
            val ddi = binding.ddiField.text.toString()

            mClickListener.hideKeyBoard()

            when {
                !isValidPhone(number) -> {
                    activity?.setErrorInput(
                        textInputLayout = binding.numberInput,
                        textInputEditText = binding.numberField
                    )
                    showSnackBar(
                        description = getString(R.string.error_number),
                        color = RED
                    )
                }
                else -> {
                    val numberPhoneFormat = (ddi + number)
                        .removePrefix(prefix = "(")
                        .removePrefix(prefix = ")")
                        .removePrefix(prefix = " ")
                        .removePrefix(prefix = "-")

                    mClickListener.onClickListener(
                        phoneNumber = numberPhoneFormat
                    )
                }
            }
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClickListener(phoneNumber: String)
        fun showKeyBoard(textInputEditText: TextInputEditText)
        fun hideKeyBoard()
    }
}