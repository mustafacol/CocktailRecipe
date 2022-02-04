package com.mustafacol.coctailrecipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.model.CocktailIngredient
import com.mustafacol.coctailrecipe.model.createIngredientMap
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import kotlinx.coroutines.*

class CocktailDetailsViewModel(
    private val repository: CocktailsRepository
) : ViewModel() {
    var ingredientMap = mutableMapOf<String, CocktailIngredient>()
    var cocktailDetails = MutableLiveData<BaseDrink>()
    var successMessage = MutableLiveData<String>()
    var job: Job? = null


    fun setIngredientMap() {
        ingredientMap = createIngredientMap(cocktailDetails.value!!)
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
        job = CoroutineScope(Dispatchers.IO).launch {
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

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}