package com.adm.recipes

import com.adm.recipes.data.api.MealDetailDto
import com.adm.recipes.domain.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MealDetailDtoTest {
    @Test
    fun `ingredients filters blank entries`() {
        val dto = MealDetailDto(
            idMeal = "1",
            strMeal = "Test",
            strCategory = "Dessert",
            strArea = "Uruguayan",
            strInstructions = "Mix and bake",
            strMealThumb = "http://thumb",
            strYoutube = "",
            strIngredient1 = "Sugar",
            strIngredient2 = "",
            strIngredient3 = "Flour",
            strIngredient4 = null,
            strIngredient5 = null,
            strIngredient6 = null,
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strMeasure1 = "1 cup",
            strMeasure2 = " ",
            strMeasure3 = "2 cups",
            strMeasure4 = null,
            strMeasure5 = null,
            strMeasure6 = null,
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null,
        )
        val ingredients = dto.ingredients()
        assertEquals(2, ingredients.size)
        assertEquals("Sugar" to "1 cup", ingredients[0])
        assertEquals("Flour" to "2 cups", ingredients[1])

        val domain = dto.toDomain()
        assertNotNull(domain)
        assertEquals(2, domain!!.ingredients.size)
    }
}
