package com.adm.recipes.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.unit.dp
import com.adm.recipes.data.repository.FilterType
import com.adm.recipes.domain.MealCategory
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.common.UiState
import com.adm.recipes.ui.components.MealCard
import com.adm.recipes.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSearch: (String) -> Unit,
    onFilter: (FilterType, String) -> Unit,
    onRandomDetail: (MealSummary) -> Unit,
    onFavorites: () -> Unit,
    onCategories: () -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var ingredient by rememberSaveable { mutableStateOf("") }
    val categoriesState by viewModel.categoriesState.collectAsStateWithLifecycle()
    val randomState by viewModel.randomState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscador de Recetas") },
                actions = {
                    IconButton(onClick = onFavorites) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().testTag("search_field"),
                label = { Text("Buscar por nombre") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
            )
            Button(
                onClick = { if (query.isNotBlank()) onSearch(query.trim()) },
                modifier = Modifier.fillMaxWidth().testTag("search_button"),
                enabled = query.isNotBlank(),
            ) {
                Text("Buscar")
            }

            OutlinedTextField(
                value = ingredient,
                onValueChange = { ingredient = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Filtrar por ingrediente") },
                singleLine = true,
            )
            Button(
                onClick = { if (ingredient.isNotBlank()) onFilter(FilterType.INGREDIENT, ingredient.trim()) },
                modifier = Modifier.fillMaxWidth(),
                enabled = ingredient.isNotBlank(),
            ) {
                Text("Buscar por ingrediente")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.loadRandomMeal() }) {
                    Icon(Icons.Default.Shuffle, null, Modifier.padding(end = 4.dp))
                    Text("Sorpresa")
                }
                AssistChip(onClick = onCategories, label = { Text("Todas las categorías") })
            }

            when (val random = randomState) {
                UiState.Loading -> CircularProgressIndicator()
                is UiState.Success -> {
                    Text("Receta aleatoria", style = MaterialTheme.typography.titleMedium)
                    MealCard(meal = random.data, onClick = { onRandomDetail(random.data) }, fullWidth = true)
                }
                is UiState.Error -> Text(random.message, color = MaterialTheme.colorScheme.error)
                null, UiState.Empty -> Unit
            }

            Text("Categorías populares", style = MaterialTheme.typography.titleMedium)
            when (val cats = categoriesState) {
                is UiState.Success -> CategoryChips(cats.data, onFilter)
                is UiState.Error -> Text(cats.message)
                UiState.Loading -> CircularProgressIndicator()
                UiState.Empty -> Text("Sin categorías")
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<MealCategory>,
    onFilter: (FilterType, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        categories.take(10).forEach { cat ->
            FilterChip(
                selected = false,
                onClick = { onFilter(FilterType.CATEGORY, cat.name) },
                label = { Text(cat.name) },
            )
        }
    }
}
