package com.example.hugohgil.catsapp.data.fakes

import androidx.paging.PagingData
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.testBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBreedRepository : BreedRepository(
    breedRetrofitApi = FakeBreedRetrofitApi(),
    breedDatabase = FakeBreedDatabase()
) {

    val updatedBreeds = mutableListOf<Breed>()
    var breedFlow: Flow<PagingData<Breed>> = flowOf(PagingData.empty())

    override suspend fun updateBreed(breed: Breed) {
        updatedBreeds.add(breed)
    }

    override fun getBreeds(): Flow<PagingData<Breed>> {
        return breedFlow
    }

    override fun getFavouriteBreeds(): Flow<PagingData<Breed>> = flowOf()
    override suspend fun getBreed(id: String): Breed = testBreed
}