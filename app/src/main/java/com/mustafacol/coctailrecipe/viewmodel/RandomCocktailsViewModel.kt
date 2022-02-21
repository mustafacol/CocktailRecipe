package com.mustafacol.coctailrecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.CocktailIngredient
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import com.mustafacol.coctailrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RandomCocktailsViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val retrofit by lazy { RetrofitInstance.retrofitInstance }
    var job: Job? = null
    var ingredientMap = mutableMapOf<String, CocktailIngredient>()
    var cocktailDetails = MutableLiveData<BaseDrink>()

    fun getRandomCocktails() {
        coroutineScope.launch {
            val response = retrofit.getRandomCocktail()

            if (response.isSuccessful && response.body() != null) {
                cocktailDetails.value = response.body()?.drinks?.get(0)
            }

        }
    }

}