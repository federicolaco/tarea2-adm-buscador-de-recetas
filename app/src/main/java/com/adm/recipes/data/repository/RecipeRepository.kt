package com.adm.recipes.data.repository

import com.adm.recipes.data.api.MealApiService
import com.adm.recipes.data.db.FavoriteDao
import com.adm.recipes.data.db.FavoriteEntity
import com.adm.recipes.domain.MealCategory
import com.adm.recipes.domain.MealDetail
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.domain.toDomain
import kotlinx.coroutines.flow.Flow

enum class FilterType {
    NAME,
    CATEGORY,
    INGREDIENT,
}

class RecipeRepository(
    private val api: MealApiService,
    private val favoriteDao: FavoriteDao,
) {
    suspend fun searchByName(query: String): Result<List<MealSummary>> = runCatching {
        api.searchByName(query.trim()).meals.orEmpty().mapNotNull { it.toDomain() }
    }

    suspend fun filter(type: FilterType, value: String): Result<List<MealSummary>> = runCatching {
        val response = when (type) {
            FilterType.NAME -> api.searchByName(value)
            FilterType.CATEGORY -> api.filterByCategory(value)
            FilterType.INGREDIENT -> api.filterByIngredient(value.replace(" ", "_"))
        }
        response.meals.orEmpty().mapNotNull { it.toDomain() }
    }

    suspend fun getMealDetail(mealId: String): Result<MealDetail> = runCatching {
        val meal = api.lookupMeal(mealId).meals?.firstOrNull()?.toDomain()
            ?: error("Receta no encontrada")
        meal
    }

    suspend fun randomMeal(): Result<MealSummary> = runCatching {
        api.randomMeal().meals?.firstOrNull()?.toDomain()
            ?: error("No se pudo obtener receta aleatoria")
    }

    suspend fun categories(): Result<List<MealCategory>> = runCatching {
        api.categories().categories.orEmpty().mapNotNull { it.toDomain() }
    }

    fun observeFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.observeAll()

    fun observeIsFavorite(mealId: String): Flow<Boolean> = favoriteDao.observeIsFavorite(mealId)

    suspend fun isFavorite(mealId: String): Boolean = favoriteDao.isFavorite(mealId)

    suspend fun toggleFavorite(summary: MealSummary) {
        if (favoriteDao.isFavorite(summary.id)) {
            favoriteDao.deleteById(summary.id)
        } else {
            favoriteDao.insert(
                FavoriteEntity(
                    idMeal = summary.id,
                    strMeal = summary.name,
                    strMealThumb = summary.thumbUrl,
                ),
            )
        }
    }

    suspend fun toggleFavorite(detail: MealDetail) {
        toggleFavorite(
            MealSummary(
                id = detail.id,
                name = detail.name,
                thumbUrl = detail.thumbUrl,
            ),
        )
    }

    suspend fun getFavoritesOnce(): List<FavoriteEntity> = favoriteDao.getAllOnce()
}
