package com.pdm.firebase.feature.presentation.fragment.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdm.firebase.databinding.DialogCodeVerificationBinding
import com.pdm.firebase.feature.presentation.base.BaseDialogFragment
import com.pdm.firebase.util.setOnSingleClickListener

class CodeVerificationDialog : BaseDialogFragment() {

    private lateinit var mClickListener: ClickListener

    private var _binding: DialogCodeVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCodeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlerClicksDialog()
    }

    private fun handlerClicksDialog() {
        binding.resendCodeNumber.setOnSingleClickListener {
            mClickListener.resendVerificationCode()
        }

        binding.verifyCode.setOnSingleClickListener {
            val code = binding.pinViewCode.text.toString()

            mClickListener.verifyPhoneNumberWithCode(
                code = code
            )
        }
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun verifyPhoneNumberWithCode(code: String)
        fun resendVerificationCode()
        fun hideKeyBoard()
    }
}