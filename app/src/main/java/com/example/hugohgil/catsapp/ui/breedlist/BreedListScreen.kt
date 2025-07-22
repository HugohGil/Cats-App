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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.hugohgil.catsapp.R
import com.example.hugohgil.catsapp.data.model.Breed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    breeds: LazyPagingItems<Breed>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    isFavouriteListScreen: Boolean,
    onNavigateToBreedDetails: (String) -> Unit,
    onToggleFavorite: (Breed) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val filteredBreeds = breeds.itemSnapshotList.items.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (!isFavouriteListScreen) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(stringResource(R.string.search_breeds)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                BreedGrid(
                    breeds = breeds,
                    filteredBreeds = filteredBreeds,
                    searchQuery = searchQuery,
                    isFavouriteListScreen = isFavouriteListScreen,
                    contentPadding = contentPadding,
                    onNavigateToBreedDetails = onNavigateToBreedDetails,
                    onToggleFavorite = onToggleFavorite
                )
            }
        }

        if (!active) {
            BreedGrid(
                breeds = breeds,
                filteredBreeds = filteredBreeds,
                searchQuery = searchQuery,
                isFavouriteListScreen = isFavouriteListScreen,
                contentPadding = contentPadding,
                onNavigateToBreedDetails = onNavigateToBreedDetails,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
fun BreedGrid(
    breeds: LazyPagingItems<Breed>,
    filteredBreeds: List<Breed>,
    searchQuery: String,
    isFavouriteListScreen: Boolean,
    contentPadding: PaddingValues,
    onNavigateToBreedDetails: (String) -> Unit,
    onToggleFavorite: (Breed) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        items(filteredBreeds.size) { index ->
            val breed = filteredBreeds[index]

            BreedItem(
                breed = breed,
                isFavouriteListScreen = isFavouriteListScreen,
                onClick = { onNavigateToBreedDetails(breed.id) },
                onToggleFavorite = { onToggleFavorite(breed) }
            )
        }

        if (searchQuery.isEmpty()) {
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
                            Text(
                                text = stringResource(R.string.error_loading_more),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BreedItem(
    breed: Breed,
    isFavouriteListScreen: Boolean,
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
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = breed.imageUrl,
                    contentDescription = stringResource(R.string.cat_breed),
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
                        contentDescription = stringResource(R.string.favourite),
                        tint = if (breed.isFavorite) Color.Yellow else Color.Gray
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isFavouriteListScreen) 100.dp else 60.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        breed.name,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier.padding(8.dp)
                    )

                    if (isFavouriteListScreen) {
                        Text(
                            stringResource(
                                R.string.life_span,
                                breed.lifeSpan?.split(" ")?.firstOrNull() ?: stringResource(R.string.not_available)
                            ),
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
