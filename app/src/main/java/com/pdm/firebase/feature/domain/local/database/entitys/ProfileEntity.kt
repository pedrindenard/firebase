package com.pdm.firebase.feature.domain.local.database.entitys

import androidx.room.*

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_profile") val id: Int = 0,
    @ColumnInfo(name = "full_name") val fullName: String = "",
    @ColumnInfo(name = "legal_document") val legalDocument: String = "",
    @ColumnInfo(name = "birth_date") val birthDate: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "gender") val gender: Int = 0,
    @ColumnInfo(name = "number_phone") var numberPhone: String = "",
    @ColumnInfo(name = "picture") var picture: String = ""
)