package com.example.hugohgil.catsapp.data.retrofitapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BreedRetrofitApiInstance {
    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}