package com.example.moviesearch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.moviesearch.api.MovieSearchItem
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(navController: NavController) {

    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModel.provideFactory(MovieRepository())
    )

    val searchQuery by movieViewModel.searchQuery.collectAsState()
    val searchResults by movieViewModel.searchResults.collectAsState()
    val isLoading by movieViewModel.isLoading.collectAsState()
    val error by movieViewModel.error.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { movieViewModel.onSearchQueryChange(it) },
                label = { Text("Movie Title") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { movieViewModel.searchMovies() }) {
                    Text("Search")
                }
            }
        }

        item {
            if (isLoading) {
                CircularProgressIndicator()
            }
            error?.let { errorMsg ->
                Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
            }
        }

        items(searchResults) { movie ->
            MovieSearchItemCard(movie = movie, onMovieClick = { imdbId ->
                navController.navigate("movie_details/$imdbId")
            })
        }
    }
}

@Composable
fun MovieSearchItemCard(movie: MovieSearchItem, onMovieClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { movie.imdbID?.let { imdbId -> onMovieClick(imdbId) } }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = if (movie.poster.isNullOrEmpty() || movie.poster == "N/A")
                    "https://via.placeholder.com/70x100.png?text=No+Image"
                else movie.poster,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .height(100.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = movie.title ?: "No title provided",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.year ?: "No year available",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
