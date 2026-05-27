package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adm.recipes.data.db.FavoriteEntity
import com.adm.recipes.data.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {
    val favorites: StateFlow<List<FavoriteEntity>> =
        repository.observeFavorites()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun remove(favorite: FavoriteEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(
                com.adm.recipes.domain.MealSummary(
                    id = favorite.idMeal,
                    name = favorite.strMeal,
                    thumbUrl = favorite.strMealThumb,
                ),
            )
        }
    }
}
