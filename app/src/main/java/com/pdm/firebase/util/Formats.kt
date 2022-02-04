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

fun TextInputEditText?.formatToLegalDocument() {
    val smf = SimpleMaskFormatter("NNN.NNN.NNN-NN")
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

inline fun <reified T : Any> Any.mapTo(): T =
    GsonBuilder().create().run {
        fromJson(toJsonTree(this@mapTo), T::class.java)
    }