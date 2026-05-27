package com.adm.recipes.ui.navigation

object Routes {
    const val HOME = "home"
    const val RESULTS = "results/{filterType}/{filterValue}"
    const val DETAIL = "detail/{mealId}"
    const val FAVORITES = "favorites"
    const val CATEGORIES = "categories"

    fun results(filterType: String, filterValue: String): String =
        "results/$filterType/${filterValue.encodeForRoute()}"

    fun detail(mealId: String): String = "detail/$mealId"
}

private fun String.encodeForRoute(): String =
    java.net.URLEncoder.encode(this, Charsets.UTF_8.name())

fun String.decodeFromRoute(): String =
    java.net.URLDecoder.decode(this, Charsets.UTF_8.name())
