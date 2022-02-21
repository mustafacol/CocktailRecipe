package com.mustafacol.coctailrecipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.model.CocktailIngredient
import com.mustafacol.coctailrecipe.model.createIngredientMap
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import com.mustafacol.coctailrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.*

class CocktailDetailsViewModel(
    private val repository: CocktailsRepository
) : ViewModel() {
    var ingredientMap = MutableLiveData<Map<String, CocktailIngredient>>()
    var cocktailDetails = MutableLiveData<BaseDrink>()
    var successMessage = MutableLiveData<String>()
    var job: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val retrofit by lazy { RetrofitInstance.retrofitInstance }


    fun setIngredientMap() {
        ingredientMap.value = createIngredientMap(cocktailDetails.value!!)!!
    }

    fun setCocktailDetails(baseDrink: BaseDrink) {
        cocktailDetails.value = baseDrink
    }

    fun getIngredientList(): MutableList<String> {
        val instructionList = mutableListOf<String>()

        val instructionStr = cocktailDetails.value?.strInstructions

        instructionStr?.trim()?.split(".")?.let {
            instructionList.addAll(it)
        }
        instructionList.removeAt(instructionList.size - 1)

        return instructionList
    }

    private suspend fun favoriteCocktail(baseDrink: BaseDrink): Long {
        val favoriteCocktail = Cocktail(
            cocktailId = baseDrink.idDrink,
            name = baseDrink.strDrink,
            category = baseDrink.strCategory,
            imageUrl = baseDrink.strDrinkThumb
        )
        return repository.insert(favoriteCocktail)
    }

    fun favoriteButtonClick() {
        job = coroutineScope.launch {
            val result = favoriteCocktail(cocktailDetails.value!!)
            withContext(Dispatchers.Main) {
                successMessage.value = if (result > 0)
                    "Success"
                else
                    "Fail"

                successMessage.value = "Hold"

            }


        }

    }

    fun getRandomCocktails() {
        job = coroutineScope.launch {
            val response = retrofit.getRandomCocktail()
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    setCocktailDetails(response.body()?.drinks?.get(0)!!)

                    setIngredientMap()
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}