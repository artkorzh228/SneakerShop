package com.example.sneakershop.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sneakershop.model.Sneaker
import kotlinx.coroutines.flow.Flow

@Dao
interface SneakerDao {
    @Query("SELECT * FROM sneakers_table")
    fun getFavorite(): Flow<List<Sneaker>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sneaker: Sneaker)

    @Delete
    suspend fun delete(sneaker: Sneaker)

    @Query("SELECT EXISTS(SELECT 1 FROM sneakers_table WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
