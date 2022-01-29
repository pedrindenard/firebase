package com.pdm.firebasestoragedatabase.feature.domain.use_case.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthException
import com.pdm.firebasestoragedatabase.feature.domain.exceptions.InvalidAuthFields
import com.pdm.firebasestoragedatabase.feature.domain.model.User
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository
import com.pdm.firebasestoragedatabase.util.isValidBirthDate
import com.pdm.firebasestoragedatabase.util.isValidEmail

class EditUserAuth(private val repository: AuthRepository) {

    @Throws(InvalidAuthException::class)
    suspend operator fun invoke(firebaseAuth: FirebaseAuth, user: User) : Task<Void>? {
        when {
            firebaseAuth.currentUser == null -> {
                throw InvalidAuthException(InvalidAuthFields.CURRENT_USER_IS_NULL.exception)
            }
            user.name.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_NAME.exception)
            }
            user.age.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_BIRTHDATE.exception)
            }
            !isValidBirthDate(user.age) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_BIRTHDATE.exception)
            }
            user.email.isBlank() -> {
                throw InvalidAuthException(InvalidAuthFields.EMPTY_EMAIL.exception)
            }
            !isValidEmail(user.email) -> {
                throw InvalidAuthException(InvalidAuthFields.INVALID_EMAIL.exception)
            }
        }

        return repository.editUserAuth(
            currentUser = firebaseAuth.currentUser!!,
            user = user
        )
    }
}