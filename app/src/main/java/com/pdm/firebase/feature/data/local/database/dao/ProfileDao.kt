package com.pdm.firebase.feature.data.local.database.dao

import androidx.room.*
import com.pdm.firebase.feature.domain.local.database.entitys.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    suspend fun selectProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(vararg profileEntity: ProfileEntity)

    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM profile")
    suspend fun deleteAllProfile()
}