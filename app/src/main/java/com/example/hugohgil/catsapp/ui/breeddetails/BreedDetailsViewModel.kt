package com.example.hugohgil.catsapp.ui.breeddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BreedDetailsViewModel(
    private val breedRepository: BreedRepository,
    private val breedId: String
) : ViewModel() {
    private val _breed = MutableStateFlow<Breed?>(null)
    val breed: StateFlow<Breed?> = _breed

    init {
        viewModelScope.launch {
            _breed.value = breedRepository.getBreed(breedId)
        }
    }

    fun toggleFavorite(breed: Breed) {
        viewModelScope.launch {
            val updatedBreed = breed.copy(isFavorite = !breed.isFavorite)

            breedRepository.updateBreed(breed = updatedBreed)
            _breed.value = updatedBreed
        }
    }
}