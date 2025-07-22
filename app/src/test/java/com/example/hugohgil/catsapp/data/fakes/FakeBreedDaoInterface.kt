package com.example.hugohgil.catsapp.data.fakes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hugohgil.catsapp.data.database.BreedDaoInterface
import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.testBreed

class FakeBreedDaoInterface : BreedDaoInterface {
    private val breeds = mutableListOf<Breed>()

    override suspend fun insertAll(breeds: List<Breed>) {
    }

    override suspend fun updateBreed(breed: Breed) {
    }

    override fun getAllBreeds(): PagingSource<Int, Breed> {
        return object : PagingSource<Int, Breed>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
                return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
            }

            override fun getRefreshKey(state: PagingState<Int, Breed>): Int? = null
        }
    }

    override fun getFavouriteBreeds(): PagingSource<Int, Breed> = getAllBreeds()

    override suspend fun getCount(): Int {
        return breeds.size
    }

    override suspend fun getBreed(id: String): Breed = testBreed
}