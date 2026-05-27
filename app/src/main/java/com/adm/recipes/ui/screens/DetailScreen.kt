package com.adm.recipes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.adm.recipes.ui.components.FavoriteIconButton
import com.adm.recipes.ui.components.RecipeScaffold
import com.adm.recipes.ui.components.StateContent
import com.adm.recipes.ui.viewmodel.DetailViewModel
import com.adm.recipes.util.IntentUtils

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.detailState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val context = LocalContext.current

    RecipeScaffold(
        title = "Detalle",
        onBack = onBack,
        actions = {
            FavoriteIconButton(isFavorite = isFavorite, onToggle = viewModel::toggleFavorite)
        },
    ) { padding ->
        StateContent(
            state = state,
            onRetry = viewModel::loadDetail,
        ) { detail ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AsyncImage(
                    model = detail.thumbUrl,
                    contentDescription = detail.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop,
                )
                Text(detail.name, style = MaterialTheme.typography.headlineSmall)
                Text(
                    "${detail.category} · ${detail.area}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { IntentUtils.shareRecipe(context, detail) }) {
                        androidx.compose.material3.Icon(Icons.Default.Share, null)
                        Text("Compartir", modifier = Modifier.padding(start = 4.dp))
                    }
                    detail.youtubeUrl?.let { url ->
                        Button(onClick = { IntentUtils.openYoutube(context, url) }) {
                            androidx.compose.material3.Icon(Icons.Default.PlayArrow, null)
                            Text("Video", modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }
                Text("Ingredientes", style = MaterialTheme.typography.titleMedium)
                detail.ingredients.forEach { line ->
                    val measure = if (line.measure.isBlank()) "" else " — ${line.measure}"
                    Text("• ${line.name}$measure", style = MaterialTheme.typography.bodyMedium)
                }
                Text("Preparación", style = MaterialTheme.typography.titleMedium)
                Text(detail.instructions, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
