package com.example.hugohgil.catsapp.ui.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.hugohgil.catsapp.data.BreedRepository

class BreedListViewModel(breedRepository: BreedRepository) : ViewModel() {

    val breeds = breedRepository.getBreeds().cachedIn(viewModelScope)
}