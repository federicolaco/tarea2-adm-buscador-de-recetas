package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealCategory
import com.adm.recipes.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<MealCategory>>>(UiState.Loading)
    val state: StateFlow<UiState<List<MealCategory>>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.categories()
                .onSuccess { list ->
                    _state.value = if (list.isEmpty()) UiState.Empty else UiState.Success(list)
                }
                .onFailure { e ->
                    _state.value = UiState.Error(e.message ?: "Error")
                }
        }
    }
}
