package com.pdm.firebase.feature.data.local.database.dao

import androidx.room.*
import com.pdm.firebase.feature.domain.local.database.entitys.WishlistEntity

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist")
    suspend fun selectWishlist(): List<WishlistEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(vararg creditCardEntity: WishlistEntity)

    @Update
    suspend fun updateWishlist(wishlistEntity: WishlistEntity)

    @Delete
    suspend fun deleteWishlist(creditCardEntity: WishlistEntity)

    @Query("DELETE FROM wishlist")
    suspend fun deleteAllWishlist()
}