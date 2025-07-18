package com.example.hugohgil.catsapp.data

import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import com.example.hugohgil.catsapp.data.database.BreedDaoInterface
import com.example.hugohgil.catsapp.data.model.Breed

class BreedRepository(
    private val retrofitApi: BreedRetrofitApiInterface,
    private val dao: BreedDaoInterface
) {
    suspend fun getBreeds(): List<Breed> {
        val storedData = dao.getAllBreeds()
        if (storedData.isNotEmpty()) {
            return storedData
        }

        val apiData = retrofitApi.getBreeds()
        dao.insertAll(apiData)

        return apiData
    }

    // for testing
    suspend fun clearDatabase() {
        dao.deleteAll()
    }
}