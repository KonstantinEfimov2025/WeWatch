package com.example.wewatch

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieController(private val repository: MovieRepository) {

    // Model: Данные из БД
    val localMovies: Flow<List<Movie>> = repository.getAllMovies()

    // State: Состояние поиска для View
    val searchResults = mutableStateListOf<Movie>()

    // State: Текущий выбранный фильм
    val selectedMovie = mutableStateOf<Movie?>(null)

    private val scope = CoroutineScope(Dispatchers.Main)

    fun searchMovies(query: String) {
        if (query.isBlank()) return
        scope.launch {
            try {
                val response = RetrofitClient.instance.searchMovies(query)
                searchResults.clear()
                if (response.searchResults != null) {
                    searchResults.addAll(response.searchResults)
                }
            } catch (e: Exception) {
                Log.e("MVC_DEBUG", "Search error: ${e.message}")
            }
        }
    }

    fun addMovie(movie: Movie) {
        scope.launch {
            repository.insert(movie)
        }
    }

    fun updateMovieCheck(movie: Movie, isChecked: Boolean) {
        scope.launch {
            repository.update(movie.copy(isChecked = isChecked))
        }
    }

    fun deleteSelectedMovies() {
        scope.launch {
            repository.deleteSelected()
        }
    }

    fun selectMovie(movie: Movie?) {
        selectedMovie.value = movie
    }
}

// Репозиторий остается частью слоя Model
class MovieRepository(private val movieDao: MovieDao) {
    fun getAllMovies() = movieDao.getAllMovies()
    suspend fun insert(movie: Movie) = movieDao.insertMovie(movie)
    suspend fun deleteSelected() = movieDao.deleteSelectedMovies()
    suspend fun update(movie: Movie) = movieDao.updateMovie(movie)
}