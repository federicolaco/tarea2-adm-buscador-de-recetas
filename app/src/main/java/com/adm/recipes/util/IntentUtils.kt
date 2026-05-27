package com.adm.recipes.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.adm.recipes.domain.MealDetail

object IntentUtils {
    fun shareRecipe(context: Context, detail: MealDetail) {
        val text = buildString {
            append(detail.name)
            append("\n")
            append("https://www.themealdb.com/meal/${detail.id}")
            detail.youtubeUrl?.let { append("\nVideo: ").append(it) }
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, detail.name)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, "Compartir receta"))
    }

    fun openYoutube(context: Context, url: String) {
        val normalized = url.trim()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(normalized))
        context.startActivity(intent)
    }
}
