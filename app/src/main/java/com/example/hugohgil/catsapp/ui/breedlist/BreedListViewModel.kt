package com.example.hugohgil.catsapp.ui.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.model.Breed
import kotlinx.coroutines.launch

class BreedListViewModel(
    private val breedRepository: BreedRepository
) : ViewModel() {
    val breeds = breedRepository.getBreeds().cachedIn(viewModelScope)

    fun toggleFavorite(breed: Breed) {
        viewModelScope.launch {
            val updatedBreed = breed.copy(isFavorite = !breed.isFavorite)

            breedRepository.updateBreed(breed = updatedBreed)
        }
    }
}