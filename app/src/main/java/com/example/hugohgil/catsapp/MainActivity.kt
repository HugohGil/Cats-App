package com.example.hugohgil.catsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.room.Room
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.database.BreedDatabase
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInstance
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import com.example.hugohgil.catsapp.ui.breeddetails.BreedDetailsScreen
import com.example.hugohgil.catsapp.ui.breeddetails.BreedDetailsViewModel
import com.example.hugohgil.catsapp.ui.breeddetails.BreedDetailsViewModelFactory
import com.example.hugohgil.catsapp.ui.breedfavouritelist.BreedFavouriteListViewModel
import com.example.hugohgil.catsapp.ui.breedfavouritelist.BreedFavouriteListViewModelFactory
import com.example.hugohgil.catsapp.ui.breedlist.BreedListScreen
import com.example.hugohgil.catsapp.ui.breedlist.BreedListViewModel
import com.example.hugohgil.catsapp.ui.breedlist.BreedListViewModelFactory
import com.example.hugohgil.catsapp.ui.theme.CatsAppTheme
import kotlinx.serialization.Serializable

@Serializable
object BreedList

@Serializable
object BreedFavouriteList

@Serializable
data class BreedDetails(val breedId: String)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsAppTheme {
                CatsApp()
            }
        }
    }
}

@Composable
fun CatsApp() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val breedListRoute = BreedList::class.qualifiedName
    val breedFavouriteListRoute = BreedFavouriteList::class.qualifiedName

    val breedRetrofitApiInterface =
        BreedRetrofitApiInstance.getInstance().create(BreedRetrofitApiInterface::class.java)
    val breedDatabase = Room.databaseBuilder(
        context,
        BreedDatabase::class.java,
        "database-breeds"
    ).build()

    val breedRepository = BreedRepository(
        breedRetrofitApi = breedRetrofitApiInterface,
        breedDatabase = breedDatabase
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(enabled = currentRoute != breedListRoute) {
                            navController.navigate(BreedList)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.cats_list),
                        color = Color.White
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(enabled = currentRoute != breedFavouriteListRoute) {
                            navController.navigate(BreedFavouriteList)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.favourites),
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BreedList
        ) {
            composable<BreedList> {
                val breedListViewModel: BreedListViewModel = viewModel(
                    factory = BreedListViewModelFactory(breedRepository)
                )
                val breeds = breedListViewModel.breeds.collectAsLazyPagingItems()

                BreedListScreen(
                    breeds = breeds,
                    contentPadding = innerPadding,
                    isFavouriteListScreen = false,
                    onNavigateToBreedDetails = { breedId ->
                        navController.navigate(BreedDetails(breedId))
                    },
                    onToggleFavorite = { breed ->
                        breedListViewModel.toggleFavorite(breed)
                    })
            }

            composable<BreedFavouriteList> {
                val breedFavouriteListViewModel: BreedFavouriteListViewModel = viewModel(
                    factory = BreedFavouriteListViewModelFactory(breedRepository)
                )
                val favouriteBreeds =
                    breedFavouriteListViewModel.breedsFavourite.collectAsLazyPagingItems()

                BreedListScreen(
                    breeds = favouriteBreeds,
                    contentPadding = innerPadding,
                    isFavouriteListScreen = true,
                    onNavigateToBreedDetails = { breedId ->
                        navController.navigate(BreedDetails(breedId))
                    },
                    onToggleFavorite = { breed ->
                        breedFavouriteListViewModel.toggleFavorite(breed)
                    }
                )
            }

            composable<BreedDetails> { backStackEntry ->
                val breedDetails: BreedDetails = backStackEntry.toRoute()
                val breedDetailsViewModel: BreedDetailsViewModel = viewModel(
                    factory = BreedDetailsViewModelFactory(
                        breedRepository = breedRepository,
                        breedId = breedDetails.breedId
                    )
                )

                BreedDetailsScreen(
                    breedDetailsViewModel = breedDetailsViewModel,
                    contentPadding = innerPadding
                )
            }
        }
    }
}
