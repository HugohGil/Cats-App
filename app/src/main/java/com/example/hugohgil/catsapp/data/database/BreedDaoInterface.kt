package com.example.hugohgil.catsapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hugohgil.catsapp.data.model.Breed

@Dao
interface BreedDaoInterface {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(breeds: List<Breed>)

    @Update
    suspend fun updateBreed(breed: Breed)

    @Query("SELECT * FROM Breed")
    fun getAllBreeds(): PagingSource<Int, Breed>

    @Query("SELECT * FROM Breed WHERE id = :id")
    suspend fun getBreed(id: String): Breed

    @Query("SELECT * FROM Breed WHERE isFavorite = 1")
    fun getFavouriteBreeds(): PagingSource<Int, Breed>

    @Query("SELECT COUNT(*) FROM Breed")
    suspend fun getCount(): Int
}