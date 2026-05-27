package com.adm.recipes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.components.MealList
import com.adm.recipes.ui.components.RecipeScaffold
import com.adm.recipes.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onBack: () -> Unit,
    onMealClick: (MealSummary) -> Unit,
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    RecipeScaffold(title = "Favoritos", onBack = onBack) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Aún no guardaste recetas.\nExplorá y tocá el corazón en el detalle.",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            MealList(
                meals = favorites.map {
                    MealSummary(id = it.idMeal, name = it.strMeal, thumbUrl = it.strMealThumb)
                },
                onMealClick = onMealClick,
                modifier = Modifier.padding(padding),
            )
        }
    }
}
