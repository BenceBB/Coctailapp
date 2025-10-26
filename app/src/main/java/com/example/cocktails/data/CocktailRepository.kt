package com.example.cocktails.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader

class CocktailRepository(private val context: Context) {

    fun loadCocktails(): List<Cocktail> {
        return runCatching {
            context.assets.open(COCKTAILS_ASSET_PATH).use { inputStream ->
                BufferedReader(inputStream.reader()).use { reader ->
                    val listType = object : TypeToken<List<CocktailRaw>>() {}.type
                    val rawItems: List<CocktailRaw> = Gson().fromJson(reader, listType)
                    rawItems.map { it.toCocktail() }
                }
            }
        }.getOrElse { emptyList() }
    }

    private data class CocktailRaw(
        val id: String,
        val name: String,
        val tagline: String,
        val imageName: String,
        val glass: String?,
        val ingredients: List<IngredientRaw>,
        val preparation: String,
        val garnish: String?
    ) {
        fun toCocktail() = Cocktail(
            id = id,
            name = name,
            tagline = tagline,
            imageName = imageName,
            glass = glass,
            ingredients = ingredients.map { it.toIngredient() },
            preparation = preparation,
            garnish = garnish
        )
    }

    private data class IngredientRaw(
        val amount: String,
        val item: String
    ) {
        fun toIngredient() = Ingredient(amount = amount, item = item)
    }

    companion object {
        private const val COCKTAILS_ASSET_PATH = "Coctaildata/cocktails.json"
    }
}
