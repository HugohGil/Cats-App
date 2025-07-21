package com.example.hugohgil.catsapp.ui.breedfavouritelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hugohgil.catsapp.data.BreedRepository

class BreedFavouriteListViewModelFactory(
    private val breedRepository: BreedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedFavouriteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreedFavouriteListViewModel(breedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}