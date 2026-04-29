package com.example.moviesearch

import com.example.moviesearch.api.OmdbMovieDetailResponse
import com.example.moviesearch.api.OmdbResponse
import com.example.moviesearch.api.RetrofitInstance

class MovieRepository {

    private val apiKey = "3b73006b" //

    suspend fun searchMovies(query: String): Result<OmdbResponse> {
        return try {
            val response = RetrofitInstance.api.searchMovies(query, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieDetails(imdbId: String): Result<OmdbMovieDetailResponse> {
        return try {
            val response = RetrofitInstance.api.getMovieDetails(imdbId, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
