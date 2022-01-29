package com.pdm.firebasestoragedatabase.util

import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText?.formatToDate() {
    val smf = SimpleMaskFormatter("NN/NN/NNNNN")
    val mtw = MaskTextWatcher(this, smf)
    this?.addTextChangedListener(mtw)
}