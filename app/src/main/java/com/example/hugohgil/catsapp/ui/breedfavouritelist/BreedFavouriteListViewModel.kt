package com.example.hugohgil.catsapp.ui.breedfavouritelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.model.Breed
import kotlinx.coroutines.launch

class BreedFavouriteListViewModel(
    private val breedRepository: BreedRepository
) : ViewModel() {
    val breedsFavourite = breedRepository.getFavouriteBreeds().cachedIn(viewModelScope)

    fun toggleFavorite(breed: Breed) {
        viewModelScope.launch {
            val updated = breed.copy(isFavorite = !breed.isFavorite)
            breedRepository.updateBreed(updated)
        }
    }
}