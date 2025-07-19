package com.example.hugohgil.catsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Breed(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String?,
    val temperament: String?,
    val description: String?,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("reference_image_id") val imageId: String?,
    val isFavorite: Boolean = false
)
