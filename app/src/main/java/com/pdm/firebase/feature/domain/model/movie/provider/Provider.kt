package com.pdm.firebase.feature.domain.model.movie.provider

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Provider(
    @SerializedName(value = "BR") val br: ProviderCountry?,
    @SerializedName(value = "US") val us: ProviderCountry?,
    @SerializedName(value = "AR") val ar: ProviderCountry?,
    @SerializedName(value = "AT") val at: ProviderCountry?,
    @SerializedName(value = "AU") val au: ProviderCountry?,
    @SerializedName(value = "BE") val be: ProviderCountry?,
    @SerializedName(value = "CA") val ca: ProviderCountry?,
    @SerializedName(value = "CH") val ch: ProviderCountry?,
    @SerializedName(value = "CL") val cl: ProviderCountry?,
    @SerializedName(value = "CO") val co: ProviderCountry?,
    @SerializedName(value = "CZ") val cz: ProviderCountry?,
    @SerializedName(value = "DE") val de: ProviderCountry?,
    @SerializedName(value = "DK") val dk: ProviderCountry?,
    @SerializedName(value = "EC") val ec: ProviderCountry?,
    @SerializedName(value = "EE") val ee: ProviderCountry?,
    @SerializedName(value = "ES") val es: ProviderCountry?,
    @SerializedName(value = "FI") val fi: ProviderCountry?,
    @SerializedName(value = "FR") val fr: ProviderCountry?,
    @SerializedName(value = "GB") val gb: ProviderCountry?,
    @SerializedName(value = "GR") val gr: ProviderCountry?,
    @SerializedName(value = "HU") val hu: ProviderCountry?,
    @SerializedName(value = "ID") val id: ProviderCountry?,
    @SerializedName(value = "IE") val ie: ProviderCountry?,
    @SerializedName(value = "IN") val `in`: ProviderCountry?,
    @SerializedName(value = "IT") val it: ProviderCountry?,
    @SerializedName(value = "JP") val jp: ProviderCountry?,
    @SerializedName(value = "KR") val kr: ProviderCountry?,
    @SerializedName(value = "LT") val lt: ProviderCountry?,
    @SerializedName(value = "LV") val lv: ProviderCountry?,
    @SerializedName(value = "MX") val mx: ProviderCountry?,
    @SerializedName(value = "MY") val my: ProviderCountry?,
    @SerializedName(value = "NL") val nl: ProviderCountry?,
    @SerializedName(value = "NO") val no: ProviderCountry?,
    @SerializedName(value = "NZ") val nz: ProviderCountry?,
    @SerializedName(value = "PE") val pe: ProviderCountry?,
    @SerializedName(value = "PH") val ph: ProviderCountry?,
    @SerializedName(value = "PL") val pl: ProviderCountry?,
    @SerializedName(value = "PT") val pt: ProviderCountry?,
    @SerializedName(value = "RO") val ro: ProviderCountry?,
    @SerializedName(value = "RU") val ru: ProviderCountry?,
    @SerializedName(value = "SE") val se: ProviderCountry?,
    @SerializedName(value = "SG") val sg: ProviderCountry?,
    @SerializedName(value = "TH") val th: ProviderCountry?,
    @SerializedName(value = "TR") val tr: ProviderCountry?,
    @SerializedName(value = "VE") val ve: ProviderCountry?,
    @SerializedName(value = "ZA") val za: ProviderCountry?,
) : Serializable