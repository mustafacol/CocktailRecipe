package com.mustafacol.coctailrecipe.model

val alphabet = listOf<String>(
    "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "g",
    "h",
    "i",
    "j",
    "k",
    "l",
    "m",
    "n",
    "o",
    "p",
    "q",
    "r",
    "s",
    "t",
    "u",
    "v",
    "w",
    "x",
    "y",
    "z"
)
val emptyBaseDrink = BaseDrink(
    dateModified = "",
    idDrink = "",
    strAlcoholic = "",
    strCategory = "",
    strCreativeCommonsConfirmed = "",
    strDrink = "",
    strDrinkAlternate = "",
    strDrinkThumb = "",
    strGlass = "",
    strIBA = "",
    strImageAttribution = "",
    strImageSource = "",
    strIngredient1 = "",
    strIngredient2 = "",
    strIngredient3 = "",
    strIngredient4 = "",
    strIngredient5 = "",
    strIngredient6 = "",
    strIngredient7 = "",
    strIngredient8 = "",
    strIngredient9 = "",
    strIngredient10 = "",
    strIngredient11 = "",
    strIngredient12 = "",
    strIngredient13 = "",
    strIngredient14 = "",
    strIngredient15 = "",
    strInstructions = "",
    strInstructionsDE = "",
    strInstructionsES = "",
    strInstructionsFR = "",
    strInstructionsIT = "",
    strInstructionsZH_HANS = "",
    strInstructionsZH_HANT = "",
    strMeasure1 = "",
    strMeasure2 = "",
    strMeasure3 = "",
    strMeasure4 = "",
    strMeasure5 = "",
    strMeasure6 = "",
    strMeasure7 = "",
    strMeasure8 = "",
    strMeasure9 = "",
    strMeasure10 = "",
    strMeasure11 = "",
    strMeasure12 = "",
    strMeasure13 = "",
    strMeasure14 = "",
    strMeasure15 = "",
    strTags = "",
    strVideo = "",
)

fun createIngredientMap(detailedCocktail: BaseDrink): MutableMap<String, CocktailIngredient> {
    var ingredientMap = mutableMapOf<String, CocktailIngredient>()
    if (detailedCocktail.strIngredient1 != null) {
        ingredientMap.put(
            "1",
            CocktailIngredient(
                detailedCocktail.strIngredient1,
                detailedCocktail.strMeasure1 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient1}.png"
            )
        )
    }
    if (detailedCocktail?.strIngredient2 != null) {
        ingredientMap.put(
            "2",
            CocktailIngredient(
                detailedCocktail.strIngredient2,
                detailedCocktail.strMeasure2 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient2}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient3 != null) {
        ingredientMap.put(
            "3",
            CocktailIngredient(
                detailedCocktail.strIngredient3,
                detailedCocktail.strMeasure3 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient3}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient4 != null) {
        ingredientMap.put(
            "4",
            CocktailIngredient(
                detailedCocktail.strIngredient4,
                detailedCocktail.strMeasure4 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient4}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient5 != null) {
        ingredientMap.put(
            "5",
            CocktailIngredient(
                detailedCocktail.strIngredient5,
                detailedCocktail.strMeasure5 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient5}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient6 != null) {
        ingredientMap.put(
            "6",
            CocktailIngredient(
                detailedCocktail.strIngredient6,
                detailedCocktail.strMeasure6 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient6}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient7 != null) {
        ingredientMap.put(
            "7",
            CocktailIngredient(
                detailedCocktail.strIngredient7,
                detailedCocktail.strMeasure7 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient7}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient8 != null) {
        ingredientMap.put(
            "8",
            CocktailIngredient(
                detailedCocktail.strIngredient8,
                detailedCocktail.strMeasure8 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient8}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient9 != null) {
        ingredientMap.put(
            "9",
            CocktailIngredient(
                detailedCocktail.strIngredient9,
                detailedCocktail.strMeasure9 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient9}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient10 != null) {
        ingredientMap.put(
            "10",
            CocktailIngredient(
                detailedCocktail.strIngredient10,
                detailedCocktail.strMeasure10 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient10}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient11 != null) {
        ingredientMap.put(
            "11",
            CocktailIngredient(
                detailedCocktail.strIngredient11,
                detailedCocktail.strMeasure11 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient11}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient12 != null) {
        ingredientMap.put(
            "12",
            CocktailIngredient(
                detailedCocktail.strIngredient12,
                detailedCocktail.strMeasure12 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient12}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient13 != null) {
        ingredientMap.put(
            "13",
            CocktailIngredient(
                detailedCocktail.strIngredient13,
                detailedCocktail.strMeasure13 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient13}.png"
            )
        )

    }
    if (detailedCocktail?.strIngredient14 != null) {
        ingredientMap.put(
            "14",
            CocktailIngredient(
                detailedCocktail.strIngredient14,
                detailedCocktail.strMeasure14 ?: "",
                "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient14}.png"
            )
        )

    }
    if (detailedCocktail.strIngredient15 != null) {
        ingredientMap["15"] = CocktailIngredient(
            detailedCocktail.strIngredient15,
            detailedCocktail.strMeasure15 ?: "",
            "https://www.thecocktaildb.com/images/ingredients/${detailedCocktail.strIngredient15}.png"
        )

    }

    return ingredientMap


}



