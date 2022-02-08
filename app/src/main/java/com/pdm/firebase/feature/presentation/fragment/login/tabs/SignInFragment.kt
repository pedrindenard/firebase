package com.pdm.firebase.feature.presentation.fragment.login.tabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentSignInBinding
import com.pdm.firebase.feature.presentation.activity.MainActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.LoginFragmentDirections
import com.pdm.firebase.feature.presentation.fragment.login.dialog.CodeVerificationDialog
import com.pdm.firebase.feature.presentation.fragment.login.dialog.GitHubLoginDialog
import com.pdm.firebase.feature.presentation.fragment.login.dialog.NumberPhoneDialog
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var activityResult: ActivityResultLauncher<Intent>
    private lateinit var dialogGitHub: GitHubLoginDialog
    private lateinit var dialogNumberPhone: NumberPhoneDialog
    private lateinit var dialogCodeVerification: CodeVerificationDialog
    private lateinit var callbackAuth: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private val viewModel by viewModel<SignInViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firebaseAuth = Firebase.auth
        firebaseAuth.useAppLanguage()
        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    viewModel.loginWithGoogle(
                        accessToken = GoogleSignIn
                            .getSignedInAccountFromIntent(result.data)
                            .getResult(ApiException::class.java)
                            .idToken
                    )
                }
                Activity.RESULT_CANCELED -> {
                    hideProgressDialog()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startMainActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.togglePassword.toggle(binding.passwordField)
        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.loginBtn.setOnSingleClickListener {
            showProgressDialog()
            loginWithUser()
            hideKeyboard()
        }

        binding.socialNetwork.google.setOnSingleClickListener {
            showProgressDialog()
            loginWithGoogle()
            hideKeyboard()
        }

        binding.socialNetwork.facebook.setOnSingleClickListener {
            showProgressDialog()
            loginWithFacebook()
            hideKeyboard()
        }

        binding.socialNetwork.github.setOnSingleClickListener {
            loginWithGitHub()
            hideKeyboard()
        }

        binding.socialNetwork.loginWithNumberPhone.setOnSingleClickListener {
            loginWithNumberPhone()
            hideKeyboard()
        }

        binding.recoveryPassword.setOnSingleClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionSignInFragmentToRecoveryPasswordFragment()
            )
            hideKeyboard()
        }
    }

    private fun initObservers() {
        viewModel.emailError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.emailInput,
                textInputEditText = binding.emailField
            )
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            activity?.setErrorInput(
                textInputLayout = binding.passwordInput,
                textInputEditText = binding.passwordField
            )
        })

        viewModel.successLoginWithUser.observe(viewLifecycleOwner, {
            hideProgressDialog()
            startMainActivity()
            emailIsVerified()
        })

        viewModel.invalidNumberError.observe(viewLifecycleOwner, {
            it?.let {
                showSnackBar(description = getString(R.string.error_number_phone), RED)
                hideProgressDialog()
            }
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            it?.let {
                showSnackBar(
                    description = if (it.isNotEmpty()) {
                        it
                    } else {
                        getString(R.string.error_register_user)
                    },
                    color = RED
                )
                hideProgressDialog()
            }
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            it?.let {
                showSnackBar(description = getString(R.string.error_fields), RED)
                hideProgressDialog()
            }
        })
    }

    private fun loginWithUser() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        viewModel.loginWithUser(
            password = password,
            email = email
        )
    }

    private fun loginWithGoogle() {
        activityResult.launch(
            GoogleSignIn.getClient(
                requireContext(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(SERVER_TOKEN_ID)
                    .requestEmail()
                    .build()
            ).signInIntent
        )
    }

    private fun loginWithFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(
            requireActivity(), callbackManager, FACEBOOK_PERMISSIONS
        )

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    viewModel.loginWithFacebook(
                        accessToken = result?.accessToken
                    )
                    showProgressDialog()
                }

                override fun onCancel() {
                    hideProgressDialog()
                }

                override fun onError(error: FacebookException) {
                    hideProgressDialog()
                }
            }
        )
    }

    private fun loginWithGitHub() {
        activity?.let {
            dialogGitHub = GitHubLoginDialog().apply {
                show(it.supportFragmentManager, DIALOG)
                setOnItemClickListener(object : GitHubLoginDialog.ClickListener {
                    override fun onClickListener(email: String) {
                        val authProvider = OAuthProvider.newBuilder(GITHUB_PROVIDER)
                        authProvider.addCustomParameter(GITHUB_PARAM_KEY, email)
                        authProvider.scopes = GITHUB_SCOPES

                        if (firebaseAuth.pendingAuthResult == null) {
                            initGitHubAuthProvider(authProvider, email)
                            showProgressDialog()
                        }
                    }

                    override fun showKeyBoard(textInputEditText: TextInputEditText) {
                        textInputEditText.showKeyBoard()
                    }

                    override fun hideKeyBoard() {
                        hideKeyboard()
                    }
                })
            }
        }
    }

    private fun initGitHubAuthProvider(authProvider: OAuthProvider.Builder, email: String) {
        viewModel.loginWithGitHub(
            firebaseAuth.startActivityForSignInWithProvider(
                requireActivity(), authProvider.build()
            ), email
        ); dialogGitHub.dismiss()
    }

    private fun loginWithNumberPhone() {
        activity?.let {
            dialogNumberPhone = NumberPhoneDialog().apply {
                show(it.supportFragmentManager, DIALOG)
                setOnItemClickListener(object : NumberPhoneDialog.ClickListener {
                    override fun onClickListener(phoneNumber: String) {
                        handlerPhoneCallback(
                            numberPhone = phoneNumber
                        )
                        PhoneAuthProvider.verifyPhoneNumber(
                            PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(requireActivity())
                                .setCallbacks(callbackAuth)
                                .build()
                        )
                        showProgressDialog()
                    }

                    override fun showKeyBoard(textInputEditText: TextInputEditText) {
                        textInputEditText.showKeyBoard()
                    }

                    override fun hideKeyBoard() {
                        hideKeyboard()
                    }
                })
            }
        }
    }

    private fun handlerPhoneCallback(numberPhone: String) {
        callbackAuth = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                dialogCodeVerification.dismiss()

                viewModel.loginWithNumberPhone(
                    credential = credential
                )
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                showSnackBar(
                    description = when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            exception.message.toString()
                        }
                        is FirebaseTooManyRequestsException -> {
                            exception.message.toString()
                        }
                        else -> {
                            getString(R.string.error_number)
                        }
                    },
                    color = RED
                )
                hideProgressDialog()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                dialogNumberPhone.dismiss()
                hideProgressDialog()

                showSnackBar(
                    description = getString(R.string.phone_code_verification),
                    color = BLACK
                )

                if (!dialogCodeVerification.isVisible) {
                    verifyCodeNumber(
                        verificationId = verificationId,
                        phoneNumber = numberPhone,
                        resendingToken = token
                    )
                }
            }
        }
    }

    private fun verifyCodeNumber(
        resendingToken: PhoneAuthProvider.ForceResendingToken,
        phoneNumber: String,
        verificationId: String
    ) {
        activity?.let {
            dialogCodeVerification = CodeVerificationDialog().apply {
                show(it.supportFragmentManager, DIALOG)
                setOnItemClickListener(object : CodeVerificationDialog.ClickListener {
                    override fun resendVerificationCode() {
                        PhoneAuthProvider.verifyPhoneNumber(
                            PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(requireActivity())
                                .setCallbacks(callbackAuth)
                                .setForceResendingToken(resendingToken)
                                .build()
                        )
                    }

                    override fun verifyPhoneNumberWithCode(code: String) {
                        PhoneAuthProvider.getCredential(verificationId, code)
                    }

                    override fun hideKeyBoard() {
                        hideKeyBoard()
                    }
                })
            }
        }
    }

    private fun startMainActivity() {
        if (firebaseAuth.currentUser?.isEmailVerified == true) {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }

    private fun emailIsVerified() {
        if (firebaseAuth.currentUser?.isEmailVerified == false) {
            showSnackBar(description = getString(R.string.sign_in_email_verify), BLACK)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}