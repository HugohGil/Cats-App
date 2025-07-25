package com.example.hugohgil.catsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.hugohgil.catsapp.data.database.BreedDatabase
import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val breedRetrofitApi: BreedRetrofitApiInterface,
    private val breedDatabase: BreedDatabase
) : RemoteMediator<Int, Breed>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Breed>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val currentItemCount = breedDatabase.breedDao().getCount()
                    currentItemCount / state.config.pageSize
                }
            }

            val breeds = breedRetrofitApi.getBreeds(
                limit = state.config.pageSize,
                page = page
            )

            val breedsWithImage = coroutineScope {
                breeds.map { breed ->
                    async {
                        if (breed.imageId != null) {
                            val breedImage = breedRetrofitApi.getBreedImage(breed.imageId)

                            breed.copy(imageUrl = breedImage.url)
                        } else {
                            breed
                        }
                    }
                }
            }.awaitAll()

            breedDatabase.withTransaction {
                breedDatabase.breedDao().insertAll(breedsWithImage)
            }

            MediatorResult.Success(endOfPaginationReached = breeds.isEmpty())
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}