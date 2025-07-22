package com.example.hugohgil.catsapp.ui.breeddetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hugohgil.catsapp.R

@Composable
fun BreedDetailsScreen(
    breedDetailsViewModel: BreedDetailsViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val breed by breedDetailsViewModel.breed.collectAsState()

    if (breed == null) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = breed!!.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = breed!!.imageUrl,
                    contentDescription = stringResource(R.string.cat_breed),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(MaterialTheme.shapes.medium)
                )

                IconButton(
                    onClick = { breedDetailsViewModel.toggleFavorite(breed!!) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(R.string.favourite),
                        tint = if (breed!!.isFavorite) Color.Yellow else Color.Gray
                    )
                }
            }


            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(
                    R.string.origin,
                    breed!!.origin ?: stringResource(R.string.not_available)
                ),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.temperament,
                    breed!!.temperament ?: stringResource(R.string.not_available)
                ),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = breed!!.description ?: stringResource(R.string.no_description),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}