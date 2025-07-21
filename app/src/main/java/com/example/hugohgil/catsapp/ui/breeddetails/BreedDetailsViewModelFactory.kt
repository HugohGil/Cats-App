package com.example.hugohgil.catsapp.ui.breeddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hugohgil.catsapp.data.BreedRepository

class BreedDetailsViewModelFactory(
    private val breedRepository: BreedRepository,
    private val breedId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BreedDetailsViewModel(breedRepository, breedId) as T
    }
}