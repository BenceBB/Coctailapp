package com.example.cocktails.data

data class Cocktail(
    val id: String,
    val name: String,
    val tagline: String,
    val imageName: String,
    val glass: String?,
    val ingredients: List<Ingredient>,
    val preparation: String,
    val garnish: String?
)

data class Ingredient(
    val amount: String,
    val item: String
)
