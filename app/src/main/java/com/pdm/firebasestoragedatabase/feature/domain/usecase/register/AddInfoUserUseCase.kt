package com.pdm.firebasestoragedatabase.feature.domain.usecase.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.AuthException
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.repository.ProfileRepository
import com.pdm.firebasestoragedatabase.util.isValidBirthDate
import com.pdm.firebasestoragedatabase.util.isValidEmail
import com.pdm.firebasestoragedatabase.util.isValidLegalDocument

class AddInfoUserUseCase(private val repository: ProfileRepository) {

    private var throws: String = ""

    @Throws(AuthException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth, user: User) : Task<Void>? {
        if (firebaseAuth.currentUser == null) {
            throw AuthException(InvalidAuth.CURRENT_USER_IS_NULL.value)
        }
        if (user.name!!.isBlank()) {
            throws += InvalidAuth.EMPTY_NAME.value
        }
        if (user.lastName!!.isBlank()) {
            throws += InvalidAuth.EMPTY_LAST_NAME.value
        }
        if (!isValidEmail(user.email!!)) {
            throws += InvalidAuth.INVALID_EMAIL.value
        }
        if (!isValidLegalDocument(user.legalDocument!!)) {
            throws += InvalidAuth.INVALID_LEGAL_DOCUMENT.value
        }
        if (!isValidBirthDate(user.birthdate!!)) {
            throws += InvalidAuth.INVALID_BIRTHDATE.value
        }
        if (throws.isNotEmpty()) {
            throw AuthException(throws)
        }

        return repository.editProfile(
            currentUser = firebaseAuth.currentUser!!,
            user = user
        )
    }
}