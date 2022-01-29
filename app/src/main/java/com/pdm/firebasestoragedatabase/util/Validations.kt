package com.pdm.firebasestoragedatabase.util

import android.util.Log
import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun isValidPassword(password: String): Boolean {
    val upperCase = Pattern.compile("[A-Z]")
    val lowerCase = Pattern.compile("[a-z]")
    val number = Pattern.compile("[0-9]")
    val special = Pattern.compile("[!@#\$%^&*()_=+{}/.<>|~]")
    var c = 0
    when {
        upperCase.matcher(password).find() &&
                lowerCase.matcher(password).find() &&
                number.matcher(password).find() -> c++

        upperCase.matcher(password).find() &&
                number.matcher(password).find() &&
                special.matcher(password).find() -> c++

        upperCase.matcher(password).find() &&
                lowerCase.matcher(password).find() &&
                special.matcher(password).find() -> c++

        lowerCase.matcher(password).find() &&
                number.matcher(password).find() &&
                special.matcher(password).find() -> c++

        upperCase.matcher(password).find() &&
                lowerCase.matcher(password).find() &&
                number.matcher(password).find() &&
                special.matcher(password).find() -> c++
    }
    return when (c) {
        1 -> true
        else -> false
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidBirthDate(date: String): Boolean {
    return try {
        val validateDate = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        validateDate.isLenient = false
        validateDate.parse(date)?.let { !it.after(Date()) } ?: true
    } catch (pe: ParseException) {
        Log.e("DATE_ERROR", pe.message.toString())
        return false
    }
}