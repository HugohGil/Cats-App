package com.example.hugohgil.catsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.hugohgil.catsapp.data.BreedRepository
import com.example.hugohgil.catsapp.data.database.BreedDatabase
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInstance
import com.example.hugohgil.catsapp.data.retrofitapi.BreedRetrofitApiInterface
import com.example.hugohgil.catsapp.ui.breedlist.BreedListScreen
import com.example.hugohgil.catsapp.ui.breedlist.BreedListViewModel
import com.example.hugohgil.catsapp.ui.breedlist.BreedListViewModelFactory
import com.example.hugohgil.catsapp.ui.theme.CatsAppTheme
import kotlinx.serialization.Serializable

@Serializable
object BreedList

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

    val breedRetrofitApi =
        BreedRetrofitApiInstance.getInstance().create(BreedRetrofitApiInterface::class.java)
    val breedDatabase = Room.databaseBuilder(
        context,
        BreedDatabase::class.java,
        "database-breeds"
    ).build()

    val breedRepository = BreedRepository(
        breedRetrofitApi = breedRetrofitApi,
        breedDatabase = breedDatabase
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BreedList
        ) {
            composable<BreedList> {
                val breedListViewModel: BreedListViewModel = viewModel(
                    factory = BreedListViewModelFactory(breedRepository)
                )

                BreedListScreen(
                    breedListViewModel = breedListViewModel,
                    contentPadding = innerPadding,
                    onNavigateToBreedDetails = { breedId ->
                        navController.navigate(BreedDetails(breedId))
                    })
            }

            composable<BreedDetails> { backStackEntry ->
                val breedDetails: BreedDetails = backStackEntry.toRoute()
                //BreedDetailsScreen(breedId = breedDetails.breedId)
            }
        }
    }
}
