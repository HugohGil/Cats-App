package com.example.hugohgil.catsapp.ui.breedlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.hugohgil.catsapp.data.model.Breed

@Composable
fun BreedListScreen(
    breeds: LazyPagingItems<Breed>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    showLifespan: Boolean,
    onNavigateToBreedDetails: (String) -> Unit,
    onToggleFavorite: (Breed) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        items(breeds.itemCount) { index ->
            val breed = breeds[index]

            if (breed != null) {
                BreedItem(
                    breed = breed,
                    showLifespan = showLifespan,
                    onClick = { onNavigateToBreedDetails(breed.id) },
                    onToggleFavorite = {onToggleFavorite(breed)}
                )
            }
        }

        breeds.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        Text(text = "Error loading more items", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun BreedItem(
    breed: Breed,
    showLifespan: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val imageUrl = breed.imageId?.let { "https://cdn2.thecatapi.com/images/$it.jpg" }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Cat Breed",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                )

                IconButton(
                    onClick = { onToggleFavorite() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Favorite",
                        tint = if (breed.isFavorite) Color.Yellow else Color.Gray
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (showLifespan) 100.dp else 60.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        breed.name,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier.padding(8.dp)
                    )

                    if (showLifespan) {
                        Text(
                            "Life Span: ${breed.lifeSpan?.split(" ")?.firstOrNull() ?: "Not Available"}",
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
