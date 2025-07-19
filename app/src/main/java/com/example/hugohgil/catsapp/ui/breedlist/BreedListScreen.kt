package com.example.hugohgil.catsapp.ui.breedlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.hugohgil.catsapp.data.model.Breed

@Composable
fun BreedListScreen(
    breedListViewModel: BreedListViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onNavigateToBreedDetails: (String) -> Unit
) {
    val breeds = breedListViewModel.breeds.collectAsLazyPagingItems()

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
                    onClick = { onNavigateToBreedDetails(breed.id) }
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
fun BreedItem(breed: Breed, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val imageUrl = breed.imageId?.let { "https://cdn2.thecatapi.com/images/$it.jpg" }

            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    breed.name,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}