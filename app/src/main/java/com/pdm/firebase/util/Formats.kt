package com.pdm.firebase.util

import android.text.Editable
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder

fun TextInputEditText?.formatToDate() {
    val smf = SimpleMaskFormatter("NN/NN/NNNNN")
    val mtw = MaskTextWatcher(this, smf)
    this?.addTextChangedListener(mtw)
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun handlerJustNumber(s: String): String {
    return try {
        val re = Regex(pattern = "[^0-9]")
        return re.replace(s, replacement = "")
    } catch (e: Exception) {
        s
    }
}

fun handlerPhone(phone: String): String {
    val phoneNumber = handlerJustNumber(phone)
    val sb = StringBuilder()
    if (phoneNumber.length in 5..8) {
        sb.append(phoneNumber.subSequence(0, 4))
        sb.append('-')
        sb.append(phoneNumber.subSequence(4, phoneNumber.length))
    } else if (phoneNumber.length == 9) {
        sb.append(phoneNumber.subSequence(0, 5))
        sb.append('-')
        sb.append(phoneNumber.subSequence(5, phoneNumber.length))
    } else if (phoneNumber.length == 10) {
        sb.append("(")
        sb.append(phoneNumber.subSequence(0, 2))
        sb.append(") ")
        sb.append(phoneNumber.subSequence(2, 6))
        sb.append("-")
        sb.append(phoneNumber.subSequence(6, phoneNumber.length))
    } else if (phoneNumber.length == 11) {
        if (phoneNumber.startsWith("0")) {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 3))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(3, 7))
            sb.append("-")
            sb.append(phoneNumber.subSequence(7, phoneNumber.length))
        } else {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 2))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(2, 7))
            sb.append("-")
            sb.append(phoneNumber.subSequence(7, phoneNumber.length))
        }
    } else if (phoneNumber.length == 12) {
        if (phoneNumber.startsWith("0")) {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 3))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(3, 8))
            sb.append("-")
            sb.append(phoneNumber.subSequence(8, phoneNumber.length))
        } else {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 2))
            sb.append(" ")
            sb.append(phoneNumber.subSequence(2, 4))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(4, 8))
            sb.append("-")
            sb.append(phoneNumber.subSequence(8, phoneNumber.length))
        }
    } else if (phoneNumber.length == 13) {
        if (phoneNumber.startsWith("0")) {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 3))
            sb.append(" ")
            sb.append(phoneNumber.subSequence(3, 5))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(5, 9))
            sb.append("-")
            sb.append(phoneNumber.subSequence(9, phoneNumber.length))
        } else {
            sb.append("(")
            sb.append(phoneNumber.subSequence(0, 2))
            sb.append(" ")
            sb.append(phoneNumber.subSequence(2, 4))
            sb.append(") ")
            sb.append(phoneNumber.subSequence(4, 9))
            sb.append("-")
            sb.append(phoneNumber.subSequence(9, phoneNumber.length))
        }
    } else if (phoneNumber.length == 14) {
        sb.append("(")
        sb.append(phoneNumber.subSequence(0, 3))
        sb.append(" ")
        sb.append(phoneNumber.subSequence(3, 5))
        sb.append(") ")
        sb.append(phoneNumber.subSequence(5, 10))
        sb.append("-")
        sb.append(phoneNumber.subSequence(10, phoneNumber.length))
    } else {
        sb.append(phoneNumber)
    }
    return sb.toString()
}

inline fun <reified T : Any> Any.mapTo(): T =
    GsonBuilder().create().run {
        fromJson(toJsonTree(this@mapTo), T::class.java)
    }