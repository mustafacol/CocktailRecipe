package com.mustafacol.coctailrecipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.BasicCocktail
import com.mustafacol.coctailrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchByIngredientViewModel : ViewModel() {

    private val retService = RetrofitInstance.retrofitInstance
    var basicCocktailList = MutableLiveData<List<BasicCocktail>>()
    var clickedCocktail = MutableLiveData<BaseDrink>()

    fun getCocktailByIngredient(ingredient: String) {

        viewModelScope.launch {
            val response = retService.getCocktailByIngredient(ingredient)

            if (response.body()?.drinks.isNullOrEmpty()) {
                basicCocktailList.value = emptyList()
            } else {
                basicCocktailList.value = response.body()?.drinks
            }

        }
    }

    suspend fun getCocktailsById(cocktailId: String) {
        withContext(viewModelScope.coroutineContext) {
            val response = retService.getCocktailsById(cocktailId)

            if (response.isSuccessful && !response.body()?.drinks.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    clickedCocktail.value = response.body()?.drinks?.get(0)

                }
            }
        }

    }
}