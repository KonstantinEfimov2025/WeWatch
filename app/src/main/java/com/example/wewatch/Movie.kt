package com.example.wewatch

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Модель данных фильма.
 * @Entity помечает класс как таблицу для Room. [cite: 14]
 */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Poster")
    val poster: String,

    @SerializedName("Type")
    val type: String? = "movie",

    // Поле для выбора фильмов на удаление
    var isChecked: Boolean = false
)

/**
 * Вспомогательный класс для получения списка результатов поиска от OMDB API. [cite: 27]
 */
data class MovieResponse(
    @SerializedName("Search")
    val searchResults: List<Movie>?,
    @SerializedName("totalResults")
    val totalResults: String?,
    @SerializedName("Response")
    val response: String
)