package com.example.hugohgil.catsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hugohgil.catsapp.data.database.BreedDatabase
import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import kotlinx.coroutines.flow.Flow

class BreedRepository(
    private val breedRetrofitApi: BreedRetrofitApiInterface,
    private val breedDatabase: BreedDatabase
) {
    suspend fun updateBreed(breed: Breed) {
        breedDatabase.breedDao().updateBreed(breed)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getBreeds(): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            remoteMediator = BreedRemoteMediator(
                breedRetrofitApi = breedRetrofitApi,
                breedDatabase = breedDatabase
            ),
            pagingSourceFactory = { breedDatabase.breedDao().getAllBreeds() }
        ).flow
    }

    fun getFavouriteBreeds(): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(pageSize = 12),
            pagingSourceFactory = { breedDatabase.breedDao().getFavouriteBreeds() }
        ).flow
    }

    suspend fun getBreed(id: String): Breed {
        return breedDatabase.breedDao().getBreed(id)
    }
}