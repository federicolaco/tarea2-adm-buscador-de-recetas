package com.adm.recipes

import com.adm.recipes.data.api.MealApiService
import com.adm.recipes.data.api.MealSummaryDto
import com.adm.recipes.data.api.MealsResponse
import com.adm.recipes.data.db.FavoriteDao
import com.adm.recipes.data.db.FavoriteEntity
import com.adm.recipes.data.repository.FilterType
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeRepositoryTest {
    @Test
    fun `searchByName maps meals`() = runBlocking {
        val api = FakeApi(
            searchResult = MealsResponse(listOf(MealSummaryDto("1", "Pasta", "thumb"))),
        )
        val repo = RecipeRepository(api, FakeDao())
        val result = repo.searchByName("pasta").getOrThrow()
        assertEquals(1, result.size)
        assertEquals("Pasta", result[0].name)
    }

    @Test
    fun `toggleFavorite inserts when not favorite`() = runBlocking {
        val dao = FakeDao()
        val repo = RecipeRepository(FakeApi(), dao)
        repo.toggleFavorite(MealSummary("9", "Pie", "t"))
        assertEquals(1, dao.items.size)
        assertEquals("9", dao.items[0].idMeal)
    }

    @Test
    fun `filter by category returns empty list`() = runBlocking {
        val api = FakeApi(categoryResult = MealsResponse(emptyList()))
        val repo = RecipeRepository(api, FakeDao())
        val result = repo.filter(FilterType.CATEGORY, "Seafood").getOrThrow()
        assertTrue(result.isEmpty())
    }

    private class FakeApi(
        private val searchResult: MealsResponse? = null,
        private val categoryResult: MealsResponse? = null,
    ) : MealApiService {
        override suspend fun searchByName(query: String) = searchResult ?: MealsResponse(null)
        override suspend fun filterByCategory(category: String) = categoryResult ?: MealsResponse(null)
        override suspend fun filterByIngredient(ingredient: String) = MealsResponse(null)
        override suspend fun filterByArea(area: String) = MealsResponse(null)
        override suspend fun lookupMeal(mealId: String) =
            com.adm.recipes.data.api.MealDetailResponse(null)
        override suspend fun randomMeal() = MealsResponse(null)
        override suspend fun categories() = com.adm.recipes.data.api.CategoriesResponse(null)
        override suspend fun listAreas(list: String) = com.adm.recipes.data.api.AreasResponse(null)
    }

    private class FakeDao : FavoriteDao {
        val items = mutableListOf<FavoriteEntity>()
        override fun observeAll(): Flow<List<FavoriteEntity>> = flowOf(items.toList())
        override suspend fun isFavorite(mealId: String) = items.any { it.idMeal == mealId }
        override fun observeIsFavorite(mealId: String): Flow<Boolean> =
            flowOf(items.any { it.idMeal == mealId })
        override suspend fun insert(favorite: FavoriteEntity) {
            items.removeAll { it.idMeal == favorite.idMeal }
            items.add(favorite)
        }
        override suspend fun delete(favorite: FavoriteEntity) {
            items.remove(favorite)
        }
        override suspend fun deleteById(mealId: String) {
            items.removeAll { it.idMeal == mealId }
        }
        override suspend fun getAllOnce(): List<FavoriteEntity> = items.toList()
    }
}
