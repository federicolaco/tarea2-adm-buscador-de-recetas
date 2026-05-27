package com.adm.recipes.domain

import com.adm.recipes.data.api.CategoryDto
import com.adm.recipes.data.api.MealDetailDto
import com.adm.recipes.data.api.MealSummaryDto

data class MealSummary(
    val id: String,
    val name: String,
    val thumbUrl: String,
)

data class MealDetail(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbUrl: String,
    val youtubeUrl: String?,
    val ingredients: List<IngredientLine>,
)

data class IngredientLine(
    val name: String,
    val measure: String,
)

data class MealCategory(
    val id: String,
    val name: String,
    val thumbUrl: String,
    val description: String,
)

fun MealSummaryDto.toDomain(): MealSummary? {
    val id = idMeal ?: return null
    val name = strMeal ?: return null
    return MealSummary(
        id = id,
        name = name,
        thumbUrl = strMealThumb.orEmpty(),
    )
}

fun MealDetailDto.toDomain(): MealDetail? {
    val id = idMeal ?: return null
    val name = strMeal ?: return null
    return MealDetail(
        id = id,
        name = name,
        category = strCategory.orEmpty(),
        area = strArea.orEmpty(),
        instructions = strInstructions.orEmpty(),
        thumbUrl = strMealThumb.orEmpty(),
        youtubeUrl = strYoutube?.takeIf { it.isNotBlank() },
        ingredients = ingredients().map { (n, m) -> IngredientLine(n, m) },
    )
}

fun CategoryDto.toDomain(): MealCategory? {
    val name = strCategory ?: return null
    return MealCategory(
        id = idCategory.orEmpty(),
        name = name,
        thumbUrl = strCategoryThumb.orEmpty(),
        description = strCategoryDescription.orEmpty(),
    )
}
