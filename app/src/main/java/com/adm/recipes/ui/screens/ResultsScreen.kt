package com.adm.recipes.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.components.MealGrid
import com.adm.recipes.ui.components.RecipeScaffold
import com.adm.recipes.ui.components.StateContent
import com.adm.recipes.ui.viewmodel.ResultsViewModel

@Composable
fun ResultsScreen(
    viewModel: ResultsViewModel,
    onBack: () -> Unit,
    onMealClick: (MealSummary) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeScaffold(title = viewModel.title, onBack = onBack) { padding ->
        StateContent(
            state = state,
            onRetry = viewModel::load,
            emptyMessage = "No hay recetas para este filtro",
            modifier = Modifier.padding(padding),
        ) { meals ->
            MealGrid(meals = meals, onMealClick = onMealClick)
        }
    }
}
