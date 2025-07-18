package com.example.hugohgil.catsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hugohgil.catsapp.data.model.Breed

@Database(entities = [Breed::class], version = 1)
abstract class BreedDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDaoInterface
}