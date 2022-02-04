package com.pdm.firebase.feature.data.local.database.dao

import androidx.room.*
import com.pdm.firebase.feature.domain.local.database.entitys.AddressEntity

@Dao
interface AddressDao {
    @Query("SELECT * FROM address")
    suspend fun selectAddress(): List<AddressEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(vararg address: AddressEntity)

    @Update
    suspend fun updateAddress(address: AddressEntity)

    @Delete
    suspend fun deleteAddress(address: AddressEntity)

    @Query("DELETE FROM address")
    suspend fun deleteAllAddress()
}