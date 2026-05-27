package com.adm.recipes

import android.app.Application
import com.adm.recipes.data.api.NetworkModule
import com.adm.recipes.data.db.AppDatabase
import com.adm.recipes.data.repository.RecipeRepository
import com.adm.recipes.receiver.ConnectivityNotifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeFinderApp : Application() {
    lateinit var repository: RecipeRepository
        private set

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.get(this)
        repository = RecipeRepository(NetworkModule.mealApi, db.favoriteDao())
        ConnectivityNotifier.listener = { online -> _isOnline.value = online }
    }
}
