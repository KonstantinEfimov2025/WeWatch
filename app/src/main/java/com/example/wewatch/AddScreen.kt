package com.example.wewatch

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    controller: MovieController,
    onMovieAdded: () -> Unit,
    onBack: () -> Unit
) {
    val movie = controller.selectedMovie.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Movie") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (movie != null) {
                OutlinedTextField(
                    value = movie.title,
                    onValueChange = {},
                    label = { Text("Movie Title") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = movie.year,
                    onValueChange = {},
                    label = { Text("Release Year") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = movie.poster,
                    contentDescription = null,
                    modifier = Modifier.height(250.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        controller.addMovie(movie)
                        onMovieAdded()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ADD MOVIE")
                }
            }
        }
    }
}