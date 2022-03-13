package com.pdm.firebase.util

import android.text.Editable
import android.text.format.DateUtils
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.pdm.firebase.feature.domain.model.credit.movie.Credit
import com.pdm.firebase.feature.domain.model.gender.Gender
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCompany
import com.pdm.firebase.feature.domain.model.movie.details.ProductionCountry
import com.pdm.firebase.feature.domain.model.movie.provider.Provider
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderCountry
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderFlatRate
import com.pdm.firebase.feature.domain.model.search.Search
import com.pdm.firebase.feature.domain.model.tv.details.CreatedBy
import okhttp3.HttpUrl
import okhttp3.Request

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

fun Float.formatDuration(): String {
    val runtime = DateUtils.formatElapsedTime(toLong())
    val formatted = runtime.replace(oldValue = ":", newValue = "h\u0020")
    return formatted + "min"
}

fun List<ProductionCountry>.formatCountry(): String {
    var productionCountry = String()
    forEachIndexed { index, country ->
        productionCountry += if (index != lastIndex) {
            "${country.name},\u0020"
        } else {
            country.name
        }
    }
    return productionCountry
}

fun List<ProductionCompany>.formatCompany(): String {
    var productionCompany = String()
    forEachIndexed { index, company ->
        productionCompany += if (index != lastIndex) {
            "${company.name},\u0020"
        } else {
            company.name
        }
    }
    return productionCompany
}

fun List<Search>.formatMovie(): String {
    var searchType = String()
    forEachIndexed { index, search ->
        searchType += if (index != lastIndex) {
            "${search.name ?: search.title},\u0020"
        } else {
            search.name ?: search.title
        }
    }
    return searchType
}

fun List<Gender>.formatGenres(): String {
    var movieGenres = String()
    forEachIndexed { index, company ->
        movieGenres += if (index != lastIndex) {
            "${company.name},\u0020"
        } else {
            company.name
        }
    }
    return movieGenres
}

fun List<Credit>.formatCrew(): String {
    var movieCrews = String()
    forEachIndexed { index, crew ->
        movieCrews += if (index != lastIndex) {
            "${crew.name},\u0020"
        } else {
            crew.name
        }
    }
    return movieCrews
}

fun List<CreatedBy>.formatCreators(): String {
    var createdBy = String()
    forEachIndexed { index, people ->
        createdBy += if (index != lastIndex) {
            "${people.name},\u0020"
        } else {
            people.name
        }
    }
    return createdBy
}

fun List<String>.formatKnowAs(): String {
    var alsoKnowAs = String()
    forEachIndexed { index, names ->
        alsoKnowAs += if (index != lastIndex) {
            "${names},\u0020"
        } else {
            names
        }
    }
    return alsoKnowAs
}

fun String.delimiterDate() = substringBefore(delimiter = "T")

fun Provider.formatToList(): MutableList<ProviderFlatRate> {
    val mutableList: MutableList<ProviderCountry> = mutableListOf()
    val providerList: MutableList<ProviderFlatRate> = mutableListOf()
    mutableList.apply {
        if (br != null) add(br); if (us != null) add(us); if (ar != null) add(ar)
        if (at != null) add(at); if (au != null) add(au); if (be != null) add(be)
        if (ca != null) add(ca); if (ch != null) add(ch); if (cl != null) add(cl)
        if (co != null) add(co); if (cz != null) add(cz); if (de != null) add(de)
        if (dk != null) add(dk); if (ec != null) add(ec); if (ee != null) add(ee)
        if (es != null) add(es); if (fi != null) add(fi); if (fr != null) add(fr)
        if (gb != null) add(gb); if (gr != null) add(gr); if (hu != null) add(hu)
        if (id != null) add(id); if (ie != null) add(ie); if (za != null) add(za)
        if (it != null) add(it); if (jp != null) add(jp); if (kr != null) add(kr)
        if (lt != null) add(lt); if (lv != null) add(lv); if (mx != null) add(mx)
        if (my != null) add(my); if (nl != null) add(nl); if (no != null) add(no)
        if (nz != null) add(nz); if (pe != null) add(pe); if (ph != null) add(ph)
        if (pl != null) add(pl); if (pt != null) add(pt); if (ro != null) add(ro)
        if (ru != null) add(ru); if (se != null) add(se); if (sg != null) add(sg)
        if (th != null) add(th); if (tr != null) add(tr); if (ve != null) add(ve)
        if (`in` != null) add(`in`)
    }
    mutableList.forEach { country ->
        country.flatRate?.forEach { flat ->
            providerList.add(flat)
        }
    }

    val hashSet: Set<ProviderFlatRate> = LinkedHashSet(providerList)
    val removedDuplicates = ArrayList(hashSet)
    return removedDuplicates.toMutableList()
}

fun String.formatDate() = substringBefore(delimiter = "-")

fun List<Int>?.formatRuntime(): String {
    this.takeIf { !it.isNullOrEmpty() }?.let { it ->
        var max = Int.MIN_VALUE
        var min = Int.MAX_VALUE

        it.forEach {
            if (it > max) max = it
            if (it < min) min = it
        }
        return "${max - (max - min)} min"
    }
    return "0 min"
}

fun Request.setLanguage() = run {
    if ("reviews".delimiterBaseUrl(this.url, MOVIE_ENDPOINT) ||
        "images".delimiterBaseUrl(this.url, MOVIE_ENDPOINT) ||
        "reviews".delimiterBaseUrl(this.url, TV_ENDPOINT) ||
        "images".delimiterBaseUrl(this.url, TV_ENDPOINT)
    ) {
        null
    } else {
        "pt-BR"
    }
}

fun String.delimiterBaseUrl(url: HttpUrl, endpoint: String) = run {
    contains(url.toString()
        .substringAfter(delimiter = endpoint)
        .substringAfter(delimiter = "/")
        .substringBefore(delimiter = "?")
    )
}