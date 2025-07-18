package com.example.hugohgil.catsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hugohgil.catsapp.data.model.Breed


@Dao
interface BreedDaoInterface {
    @Query("SELECT * FROM Breed")
    suspend fun getAllBreeds(): List<Breed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(breeds: List<Breed>)

    @Query("DELETE FROM Breed")
    suspend fun deleteAll()
}