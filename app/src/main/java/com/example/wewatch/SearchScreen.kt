package com.example.wewatch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    controller: MovieController,
    onMovieSelected: (Movie) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Enter movie title...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                if (searchQuery.isNotEmpty()) {
                                    controller.searchMovies(searchQuery)
                                }
                            }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (controller.searchResults.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Type something and press search icon")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                items(controller.searchResults) { movie ->
                    SearchMovieItem(movie = movie, onClick = { onMovieSelected(movie) })
                }
            }
        }
    }
}

@Composable
fun SearchMovieItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = movie.year, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Type: ${movie.type ?: "N/A"}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}