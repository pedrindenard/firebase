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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentSignInBinding
import com.pdm.firebase.feature.presentation.activity.MainActivity
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.login.LoginFragmentDirections
import com.pdm.firebase.feature.presentation.fragment.login.dialog.GitHubDialog
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var activityResult: ActivityResultLauncher<Intent>
    private lateinit var dialogFragment: GitHubDialog

    private val viewModel by viewModel<SignInViewModel>()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firebaseAuth = Firebase.auth
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

        viewModel.successCreatedUserWithSocialLogin.observe(viewLifecycleOwner, {
            findNavController().navigate(
                LoginFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
            hideProgressDialog()
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

        viewModel.dismissDialogFragment.observe(viewLifecycleOwner, {
            it.let { dialogFragment.dismiss() }
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
            dialogFragment = GitHubDialog().apply {
                show(it.supportFragmentManager, DIALOG_GITHUB)
                setOnItemClickListener(object : GitHubDialog.ClickListener {
                    override fun onClickListener(email: String) {
                        val authProvider = OAuthProvider.newBuilder(GITHUB_PROVIDER)
                        authProvider.addCustomParameter(GITHUB_PARAM_KEY, email)
                        authProvider.scopes = GITHUB_SCOPES

                        if (firebaseAuth.pendingAuthResult == null) {
                            initAuthProvider(authProvider, email)
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

    private fun loginWithNumberPhone() {
        viewModel.loginWithNumberPhone(
            numberPhone = ""
        )
    }

    private fun initAuthProvider(authProvider: OAuthProvider.Builder, email: String) {
        viewModel.loginWithGitHub(
            firebaseAuth.startActivityForSignInWithProvider(
                requireActivity(), authProvider.build()
            ), email
        )
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
        viewModel.invalidateErrors()
        _binding = null
    }
}