/*

Assignment 9 Questions and Answers

Q1: Explain the role of Retrofit in this project.
A1: Retrofit is used to make HTTP requests to the OMDB API. Instead of manually handling URLs, HTTP methods, and JSON parsing, Retrofit does all that for us and automatically converts JSON responses into our data classes.

Q2: What is the purpose of the data classes (e.g., OmdbMovieDetailResponse, MovieSearchItem)?
A2: The data classes are models for the data we get from the OMDB API. Each property in the data class matches a field in the JSON response. This allows us to safely access movie info like title, year, poster, etc., without worrying about missing fields because we made them nullable (e.g., String?).

Q3: Why is a MovieRepository included in this project's architecture?
A3: The repository is a separate layer that talks to Retrofit and gets the data. This keeps our ViewModel clean and makes it easier to manage data. If later we wanted to get movies from a database instead of the API, we would just change the repository, not the UI.

Q4: What are the primary responsibilities of the MovieViewModel?
A4: The ViewModel keeps UI data like the search query, search results, loading state, and errors. It calls the repository to get movies and exposes data as StateFlow. This separates UI logic from data logic and survives configuration changes like screen rotation.

Q5: Describe how navigation between the MovieSearchScreen and MovieDetailsScreen is handled.
A5: We use Jetpack Navigation with a NavHost. Each screen is a composable. When a movie is clicked in MovieSearchScreen, we navigate to MovieDetailsScreen and pass the movie’s imdbId as an argument. MovieDetailsScreen uses that imdbId to get the full movie details from the ViewModel.

*/
package com.example.moviesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.api.MovieSearchItem
import com.example.moviesearch.api.OmdbMovieDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MovieSearchItem>>(emptyList())
    val searchResults: StateFlow<List<MovieSearchItem>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies() {
        _isLoading.value = true
        _error.value = null
        _searchResults.value = emptyList()

        viewModelScope.launch {
            val query = _searchQuery.value
            if (query.isBlank()) {
                _error.value = "Search query cannot be empty"
                _isLoading.value = false
                return@launch
            }
            repository.searchMovies(query)
                .onSuccess { response ->
                    if (response.response == "True" && response.search != null) {
                        _searchResults.value = response.search
                    } else {
                        _error.value = response.error ?: "No movies found for that title."
                    }
                }
                .onFailure { e ->
                    _error.value = e.message ?: "An unknown error occurred."
                }
            _isLoading.value = false
        }
    }

    suspend fun getMovieDetailsById(imdbId: String): OmdbMovieDetailResponse? {
        _isLoading.value = true
        _error.value = null

        val result = repository.getMovieDetails(imdbId)
        _isLoading.value = false

        return result.getOrElse { e ->
            _error.value = e.message ?: "An error occurred fetching movie details."
            null
        }.takeIf { it?.response == "True" }
    }

    // Factory for ViewModel injection
    companion object {
        fun provideFactory(repository: MovieRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                        return MovieViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}