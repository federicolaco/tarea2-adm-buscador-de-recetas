package com.adm.recipes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.adm.recipes.domain.MealCategory
import com.adm.recipes.ui.components.RecipeScaffold
import com.adm.recipes.ui.components.StateContent
import com.adm.recipes.ui.viewmodel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    onBack: () -> Unit,
    onCategoryClick: (MealCategory) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeScaffold(title = "Categorías", onBack = onBack) { padding ->
        StateContent(
            state = state,
            onRetry = viewModel::load,
            modifier = Modifier.padding(padding),
        ) { categories ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(categories, key = { it.id + it.name }) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickable { onCategoryClick(category) },
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AsyncImage(
                                model = category.thumbUrl,
                                contentDescription = category.name,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop,
                            )
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(category.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    category.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
