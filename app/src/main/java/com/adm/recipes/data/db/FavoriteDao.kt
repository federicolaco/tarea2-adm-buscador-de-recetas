package com.adm.recipes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_meals ORDER BY savedAt DESC")
    fun observeAll(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :mealId)")
    suspend fun isFavorite(mealId: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :mealId)")
    fun observeIsFavorite(mealId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_meals WHERE idMeal = :mealId")
    suspend fun deleteById(mealId: String)

    @Query("SELECT * FROM favorite_meals ORDER BY savedAt DESC")
    suspend fun getAllOnce(): List<FavoriteEntity>
}
