package com.adm.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.adm.recipes.data.repository.RecipeRepository

class ViewModelFactory(
    private val repository: RecipeRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T
            modelClass.isAssignableFrom(ResultsViewModel::class.java) ->
                ResultsViewModel(repository, savedStateHandle) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(repository, savedStateHandle) as T
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(repository) as T
            modelClass.isAssignableFrom(CategoriesViewModel::class.java) ->
                CategoriesViewModel(repository) as T
            else -> error("Unknown ViewModel: ${modelClass.name}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        create(modelClass, CreationExtras.Empty)
}
