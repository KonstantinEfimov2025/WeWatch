package com.example.wewatch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Получаем все фильмы из базы данных.
    // Flow позволяет автоматически обновлять UI, когда данные в таблице меняются.
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    // Добавляем фильм. Если такой фильм уже есть, заменяем его.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    // ОБНОВЛЕНО: Метод для обновления существующего фильма (нужен для MVC и корректного удаления)
    @Update
    suspend fun updateMovie(movie: Movie)

    // Удаляем конкретный фильм по ID
    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    // Удаляем все фильмы, у которых стоит галочка isChecked = true (значение 1 в SQLite)
    @Query("DELETE FROM movies WHERE isChecked = 1")
    suspend fun deleteSelectedMovies()
}