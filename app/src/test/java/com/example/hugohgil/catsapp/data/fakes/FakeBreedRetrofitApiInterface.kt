package com.example.hugohgil.catsapp.data.fakes

import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface

class FakeBreedRetrofitApi : BreedRetrofitApiInterface {
    override suspend fun getBreeds(limit: Int, page: Int): List<Breed> {
        return emptyList()
    }
}