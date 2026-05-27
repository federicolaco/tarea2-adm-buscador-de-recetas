package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealDetail
import com.adm.recipes.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val mealId: String = savedStateHandle.get<String>("mealId").orEmpty()

    private val _detailState = MutableStateFlow<UiState<MealDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<MealDetail>> = _detailState.asStateFlow()

    val isFavorite: StateFlow<Boolean> = repository.observeIsFavorite(mealId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    init {
        if (mealId.isBlank()) {
            _detailState.value = UiState.Error("ID de receta inválido")
        } else {
            loadDetail()
        }
    }

    fun loadDetail() {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            repository.getMealDetail(mealId)
                .onSuccess { _detailState.value = UiState.Success(it) }
                .onFailure { e ->
                    _detailState.value = UiState.Error(e.message ?: "No se pudo cargar la receta")
                }
        }
    }

    fun toggleFavorite() {
        val detail = (_detailState.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            repository.toggleFavorite(detail)
        }
    }
}
