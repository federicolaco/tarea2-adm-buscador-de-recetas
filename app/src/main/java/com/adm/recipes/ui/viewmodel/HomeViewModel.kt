package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealCategory
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {
    private val _categoriesState = MutableStateFlow<UiState<List<MealCategory>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<MealCategory>>> = _categoriesState.asStateFlow()

    private val _randomState = MutableStateFlow<UiState<MealSummary>?>(null)
    val randomState: StateFlow<UiState<MealSummary>?> = _randomState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = UiState.Loading
            repository.categories()
                .onSuccess { list ->
                    _categoriesState.value = if (list.isEmpty()) UiState.Empty else UiState.Success(list)
                }
                .onFailure { e ->
                    _categoriesState.value = UiState.Error(e.message ?: "Error al cargar categorías")
                }
        }
    }

    fun loadRandomMeal() {
        viewModelScope.launch {
            _randomState.value = UiState.Loading
            repository.randomMeal()
                .onSuccess { _randomState.value = UiState.Success(it) }
                .onFailure { e ->
                    _randomState.value = UiState.Error(e.message ?: "Error")
                }
        }
    }

    fun clearRandom() {
        _randomState.value = null
    }
}
