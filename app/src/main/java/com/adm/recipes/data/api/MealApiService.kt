package com.adm.recipes.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchByName(@Query("s") query: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByArea(@Query("a") area: String): MealsResponse

    @GET("lookup.php")
    suspend fun lookupMeal(@Query("i") mealId: String): MealDetailResponse

    @GET("random.php")
    suspend fun randomMeal(): MealsResponse

    @GET("categories.php")
    suspend fun categories(): CategoriesResponse

    @GET("list.php")
    suspend fun listAreas(@Query("a") list: String = "list"): AreasResponse
}
