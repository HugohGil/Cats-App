package com.example.hugohgil.catsapp.data.retrofitapi

import com.example.hugohgil.catsapp.data.model.Breed
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedRetrofitApiInterface {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int = 12,
        @Query("page") page: Int = 0
    ): List<Breed>
}