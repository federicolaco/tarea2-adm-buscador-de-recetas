package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adm.recipes.data.repository.FilterType
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.common.UiState
import com.adm.recipes.ui.navigation.decodeFromRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val filterType: FilterType =
        FilterType.valueOf(savedStateHandle.get<String>("filterType") ?: FilterType.NAME.name)
    private val filterValue: String =
        savedStateHandle.get<String>("filterValue")?.decodeFromRoute().orEmpty()

    private val _state = MutableStateFlow<UiState<List<MealSummary>>>(UiState.Loading)
    val state: StateFlow<UiState<List<MealSummary>>> = _state.asStateFlow()

    val title: String = filterValue.ifBlank { "Resultados" }

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.filter(filterType, filterValue)
                .onSuccess { list ->
                    _state.value = when {
                        list.isEmpty() -> UiState.Empty
                        else -> UiState.Success(list)
                    }
                }
                .onFailure { e ->
                    _state.value = UiState.Error(e.message ?: "Error de red")
                }
        }
    }
}
