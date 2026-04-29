package com.example.moviesearch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import com.example.moviesearch.api.OmdbMovieDetailResponse
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(navController: NavController, imdbId: String) {

    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModel.provideFactory(MovieRepository())
    )

    val coroutineScope = rememberCoroutineScope()
    var movieDetails by remember { mutableStateOf<OmdbMovieDetailResponse?>(null) }

    LaunchedEffect(imdbId) {
        coroutineScope.launch {
            movieDetails = movieViewModel.getMovieDetailsById(imdbId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        movieDetails?.let { movie ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = if (movie.poster.isNullOrEmpty() || movie.poster == "N/A")
                        "https://via.placeholder.com/300x400.png?text=No+Image"
                    else movie.poster,
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title ?: "No title",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Year: ${movie.year ?: "N/A"}")
                Text(text = "Rated: ${movie.rated ?: "N/A"}")
                Text(text = "Director: ${movie.director ?: "N/A"}")
                Text(text = "Actors: ${movie.actors ?: "N/A"}")
                Text(
                    text = "Rotten Tomatoes: ${movie.ratings?.find { it.source == "Rotten Tomatoes" }?.value ?: "N/A"}"
                )
                Text(text = "IMDb Rating: ${movie.imdbRating ?: "N/A"}")
                Text(text = "Box Office: ${movie.boxOffice ?: "N/A"}")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = movie.plot ?: "No plot available")
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
