package com.example.hugohgil.catsapp.data

import com.example.hugohgil.catsapp.data.model.Breed
import com.example.hugohgil.catsapp.data.model.BreedImage

val testBreed = Breed(
    id = "test",
    name = "Test Breed",
    origin = "Portugal",
    temperament = "Curious",
    description = "A breed for testing",
    lifeSpan = "15 - 18",
    imageUrl = "testurl",
    imageId = "imagetest",
    isFavorite = false
)

val testBreedImage = BreedImage(
    id = "testImage",
    url = "testurl"
)