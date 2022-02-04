package com.pdm.firebase.util

import android.util.Log
import android.util.Patterns
import java.security.MessageDigest
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

fun isValidLegalDocument(cpf: String): Boolean {

    val numbers = arrayListOf<Int>()

    cpf.filter { it.isDigit() }.forEach {
        numbers.add(it.toString().toInt())
    }

    if (numbers.size != 11) return false

    (0..9).forEach { n ->
        val digits = arrayListOf<Int>()
        (0..10).forEach { _ -> digits.add(n) }
        if (numbers == digits) return false
    }

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

fun String.hashConfig(): String? {
    return try {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(this.toByteArray(charset("UTF-8")))
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        hexString.toString()
    } catch (ex: Exception) {
        throw RuntimeException(ex)
    }
}

fun isValidPhone(phone: String): Boolean {
    if (handlerJustNumber(phone).length == 10) {
        if (handlerJustNumber(phone) != "0000000000") return true
    } else if (handlerJustNumber(phone).length == 11) {
        if (handlerJustNumber(phone) != "00000000000" &&
            handlerJustNumber(phone).startsWith(prefix = "9", startIndex = 2)
        ) return true
    }
    return false
}