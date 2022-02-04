package com.pdm.firebase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebase.feature.domain.enums.InvalidAuth
import com.pdm.firebase.feature.domain.model.auth.User
import com.pdm.firebase.feature.domain.repository.ProfileRepository
import com.pdm.firebase.util.isValidBirthDate
import com.pdm.firebase.util.isValidEmail
import com.pdm.firebase.util.isValidLegalDocument

class AddInfoUserUseCase(private val repository: ProfileRepository) {

    @Throws(Exception::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth, user: User) : Task<Void>? {
        var throws = String()

        if (firebaseAuth.currentUser == null) {
            throw Exception(InvalidAuth.CURRENT_USER_IS_NULL.value)
        }
        if (user.name.isBlank()) {
            throws += InvalidAuth.EMPTY_NAME.value
        }
        if (user.fullName.isBlank()) {
            throws += InvalidAuth.EMPTY_LAST_NAME.value
        }
        if (!isValidEmail(user.email)) {
            throws += InvalidAuth.INVALID_EMAIL.value
        }
        if (!isValidLegalDocument(user.legalDocument)) {
            throws += InvalidAuth.INVALID_LEGAL_DOCUMENT.value
        }
        if (!isValidBirthDate(user.birthdate)) {
            throws += InvalidAuth.INVALID_BIRTHDATE.value
        }
        if (throws.isNotEmpty()) {
            throw Exception(throws)
        }

        return repository.editProfile(
            currentUser = firebaseAuth.currentUser!!,
            user = user
        )
    }
}