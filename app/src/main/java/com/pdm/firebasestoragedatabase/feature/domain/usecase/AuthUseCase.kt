package com.pdm.firebasestoragedatabase.feature.domain.usecase

import com.pdm.firebasestoragedatabase.feature.domain.usecase.login.*
import com.pdm.firebasestoragedatabase.feature.domain.usecase.register.*
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.LogOutUseCase

data class AuthUseCase (
    val loginWithUserUseCase: LoginWithUserUseCase,
    val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    val loginWithFacebookUseCase: LoginWithFacebookUseCase,
    val loginWithGitHubUseCase: LoginWithGitHubUseCase,
    val recoveryUserUseCase: RecoveryPasswordUseCase,

    val registerUserUseCase: RegisterUserUseCase,
    val addInfoUserUseCase: AddInfoUserUseCase,
    val emailVerificationUseCase: EmailVerificationUseCase,

    val getUserInfoUseCase: GetUserInfoUseCase,
    val editUserUseCase: EditUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val logOutUseCase: LogOutUseCase
)

