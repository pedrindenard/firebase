package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.login.*
import com.pdm.firebase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoSocialUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebase.feature.domain.usecase.register.RegisterUserUseCase

data class AuthUseCase (
    val loginWithUserUseCase: LoginWithUserUseCase,
    val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    val loginWithFacebookUseCase: LoginWithFacebookUseCase,
    val loginWithGitHubUseCase: LoginWithGitHubUseCase,
    val loginWithNumberProneUseCase: LoginWithNumberProneUseCase,
    val recoveryUserUseCase: RecoveryPasswordUseCase,

    val addInfoSocialUserUseCase: AddInfoSocialUserUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val addInfoUserUseCase: AddInfoUserUseCase,
    val emailVerificationUseCase: EmailVerificationUseCase,

    val getUserInfoUseCase: GetUserInfoUseCase,
    val editUserUseCase: EditUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase
)

