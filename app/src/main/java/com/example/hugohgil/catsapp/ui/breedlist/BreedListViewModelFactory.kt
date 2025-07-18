package com.example.hugohgil.catsapp.ui.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hugohgil.catsapp.data.BreedRepository

class BreedListViewModelFactory(
    private val breedRepository: BreedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreedListViewModel(breedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}