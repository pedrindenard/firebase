package com.pdm.firebase.feature.presentation.fragment.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.pdm.firebase.R
import com.pdm.firebase.databinding.DialogGitHubLoginBinding
import com.pdm.firebase.feature.presentation.base.BaseDialogFragment
import com.pdm.firebase.util.RED
import com.pdm.firebase.util.handlerDelay
import com.pdm.firebase.util.isValidEmail
import com.pdm.firebase.util.setOnSingleClickListener

class GitHubLoginDialog : BaseDialogFragment() {

    private lateinit var mClickListener: ClickListener

    private var _binding: DialogGitHubLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogGitHubLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginWithGitHub.setOnSingleClickListener {
            val email = binding.emailField.text.toString()

            mClickListener.hideKeyBoard()

            when {
                !isValidEmail(email) -> {
                    binding.emailInput.setErrorInput(
                        textInputEditText = binding.emailField
                    )
                    showSnackBar(
                        description = getString(R.string.error_email),
                        color = RED
                    )
                }
                else -> {
                    mClickListener.onClickListener(email = email)
                }
            }
        }

        handlerDelay(delayMillis = 300) {
            mClickListener.showKeyBoard(
                textInputEditText = binding.emailField
            )
        }

        binding.emailInput.defaultStateColor()
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClickListener(email: String)
        fun showKeyBoard(textInputEditText: TextInputEditText)
        fun hideKeyBoard()
    }
}