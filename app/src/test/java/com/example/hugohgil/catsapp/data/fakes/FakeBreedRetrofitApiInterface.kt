package com.example.hugohgil.catsapp.data.fakes

import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.model.BreedImage
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import com.example.hugohgil.catsapp.data.testBreedImage

class FakeBreedRetrofitApi : BreedRetrofitApiInterface {
    override suspend fun getBreeds(limit: Int, page: Int): List<Breed> {
        return emptyList()
    }

    override suspend fun getBreedImage(imageId: String): BreedImage = testBreedImage
}