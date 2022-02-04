package com.pdm.firebase.feature.domain.local.database.entitys

import androidx.room.*

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_address") val id: Int = 0,
    @ColumnInfo(name = "address_name") val addressName: String = "",
    @ColumnInfo(name = "zip_code") val zipCode: String = "",
    @ColumnInfo(name = "country") val country: String = "",
    @ColumnInfo(name = "state") val state: String = "",
    @ColumnInfo(name = "city") val city: String = "",
    @ColumnInfo(name = "street") val street: String = "",
    @ColumnInfo(name = "number") val number: String = "",
    @ColumnInfo(name = "complement") val complement: String = "",
    @ColumnInfo(name = "neighborhood") val neighborhood: String = "",
    @ColumnInfo(name = "default_address") val defaultAddress: Boolean = false
)