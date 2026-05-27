package com.adm.recipes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adm.recipes.data.repository.FilterType
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.domain.MealSummary
import com.adm.recipes.ui.screens.CategoriesScreen
import com.adm.recipes.ui.screens.DetailScreen
import com.adm.recipes.ui.screens.FavoritesScreen
import com.adm.recipes.ui.screens.HomeScreen
import com.adm.recipes.ui.screens.ResultsScreen
import com.adm.recipes.ui.viewmodel.CategoriesViewModel
import com.adm.recipes.ui.viewmodel.DetailViewModel
import com.adm.recipes.ui.viewmodel.FavoritesViewModel
import com.adm.recipes.ui.viewmodel.HomeViewModel
import com.adm.recipes.ui.viewmodel.ResultsViewModel
import com.adm.recipes.ui.viewmodel.ViewModelFactory

@Composable
fun RecipeNavGraph(
    repository: RecipeRepository,
    navController: NavHostController = rememberNavController(),
    factory: ViewModelFactory = ViewModelFactory(repository),
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = viewModel(factory = factory)
            HomeScreen(
                viewModel = vm,
                onSearch = { query ->
                    navController.navigate(Routes.results(FilterType.NAME.name, query))
                },
                onFilter = { type, value ->
                    navController.navigate(Routes.results(type.name, value))
                },
                onRandomDetail = { meal -> navController.navigate(Routes.detail(meal.id)) },
                onFavorites = { navController.navigate(Routes.FAVORITES) },
                onCategories = { navController.navigate(Routes.CATEGORIES) },
            )
        }

        composable(
            route = Routes.RESULTS,
            arguments = listOf(
                navArgument("filterType") { type = NavType.StringType },
                navArgument("filterValue") { type = NavType.StringType },
            ),
        ) {
            val vm: ResultsViewModel = viewModel(factory = factory)
            ResultsScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onMealClick = { meal -> navController.navigate(Routes.detail(meal.id)) },
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType }),
        ) {
            val vm: DetailViewModel = viewModel(factory = factory)
            DetailScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.FAVORITES) {
            val vm: FavoritesViewModel = viewModel(factory = factory)
            FavoritesScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onMealClick = { meal: MealSummary ->
                    navController.navigate(Routes.detail(meal.id))
                },
            )
        }

        composable(Routes.CATEGORIES) {
            val vm: CategoriesViewModel = viewModel(factory = factory)
            CategoriesScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onCategoryClick = { cat ->
                    navController.navigate(Routes.results(FilterType.CATEGORY.name, cat.name))
                },
            )
        }
    }
}
