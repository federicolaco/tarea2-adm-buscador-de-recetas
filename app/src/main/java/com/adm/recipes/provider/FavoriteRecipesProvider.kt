package com.adm.recipes.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.adm.recipes.data.db.AppDatabase
import kotlinx.coroutines.runBlocking

/**
 * Expone favoritos guardados en Room para cumplir el requisito de ContentProvider.
 * URI: content://com.adm.recipes.favorites/favorites
 */
class FavoriteRecipesProvider : ContentProvider() {
    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        if (uriMatcher.match(uri) != FAVORITES) return null
        val dao = AppDatabase.get(requireNotNull(context)).favoriteDao()
        val rows = runBlocking { dao.getAllOnce() }
        val cursor = MatrixCursor(arrayOf("idMeal", "strMeal", "strMealThumb", "savedAt"))
        rows.forEach { fav ->
            cursor.addRow(arrayOf(fav.idMeal, fav.strMeal, fav.strMealThumb, fav.savedAt))
        }
        return cursor
    }

    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        FAVORITES -> "vnd.android.cursor.dir/vnd.com.adm.recipes.favorites"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int = 0

    companion object {
        const val AUTHORITY = "com.adm.recipes.favorites"
        private const val FAVORITES = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "favorites", FAVORITES)
        }
    }
}
