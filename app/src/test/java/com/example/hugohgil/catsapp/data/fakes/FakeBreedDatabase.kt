package com.example.hugohgil.catsapp.data.fakes

import androidx.room.InvalidationTracker
import com.example.hugohgil.catsapp.data.database.BreedDaoInterface
import com.example.hugohgil.catsapp.data.database.BreedDatabase

class FakeBreedDatabase : BreedDatabase() {
    private val fakeBreedDao = FakeBreedDaoInterface()

    override fun breedDao(): BreedDaoInterface = fakeBreedDao

    override fun clearAllTables() {
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return InvalidationTracker(this, emptyMap(), emptyMap())
    }
}