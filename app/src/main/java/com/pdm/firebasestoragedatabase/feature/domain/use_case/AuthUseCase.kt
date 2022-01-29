package com.pdm.firebasestoragedatabase.feature.domain.use_case

import com.pdm.firebasestoragedatabase.feature.domain.use_case.auth.*

data class AuthUseCase (
    val loginUser: LoginUserAuth,
    val editUser: EditUserAuth,
    val registerUser: RegisterUserAuth,
    val deleteUser: DeleteUserAuth,
    val recoveryUser: RecoveryUserAuth,
    val submitEmailUserAuth: SubmitEmailUserAuth
)