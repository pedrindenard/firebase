package com.pdm.firebase.feature.domain.usecase.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pdm.firebase.feature.domain.repository.LoginRepository

class LoginWithGitHubUseCase(private val repository: LoginRepository) {

    suspend operator fun invoke(task: Task<AuthResult>): Task<AuthResult>? {
        return repository.loginWithGitHub(
            task = task
        )
    }
}