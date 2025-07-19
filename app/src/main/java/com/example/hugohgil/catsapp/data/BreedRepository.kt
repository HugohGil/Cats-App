package com.example.hugohgil.catsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import com.example.hugohgil.catsapp.data.database.BreedDatabase
import com.example.hugohgil.catsapp.data.model.Breed
import kotlinx.coroutines.flow.Flow

class BreedRepository(
    private val breedRetrofitApi: BreedRetrofitApiInterface,
    private val breedDatabase: BreedDatabase
) {
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
}