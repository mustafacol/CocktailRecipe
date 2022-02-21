package com.mustafacol.coctailrecipe.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicCocktail(val strDrink: String, val strDrinkThumb: String, val idDrink: String) : Parcelable

@Parcelize
class BasicCocktailResponse : Parcelable {
    @SerializedName("drinks")
    var drinks: ArrayList<BasicCocktail>? = arrayListOf()
}
