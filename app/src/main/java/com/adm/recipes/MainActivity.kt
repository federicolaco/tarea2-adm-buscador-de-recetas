package com.adm.recipes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adm.recipes.service.RecipeCacheService
import com.adm.recipes.ui.navigation.RecipeNavGraph
import com.adm.recipes.ui.theme.RecipeFinderTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as RecipeFinderApp
        startService(Intent(this, RecipeCacheService::class.java))

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val isOnline by app.isOnline.collectAsStateWithLifecycle()

            LaunchedEffect(isOnline) {
                if (!isOnline) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Sin conexión. Mostrando datos guardados.")
                    }
                }
            }

            RecipeFinderTheme {
                androidx.compose.material3.Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
                    RecipeNavGraph(repository = app.repository)
                }
            }
        }

        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val mealId = intent?.getStringExtra(EXTRA_MEAL_ID) ?: return
        Toast.makeText(this, "Abrir receta $mealId desde servicio", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_MEAL_ID = "extra_meal_id"
    }
}
