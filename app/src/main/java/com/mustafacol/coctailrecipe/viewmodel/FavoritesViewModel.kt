package com.mustafacol.coctailrecipe.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafacol.coctailrecipe.db.CocktailsDatabase
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import kotlinx.coroutines.*

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { CocktailsDatabase.databaseInstance(getApplication()).cocktailsDAO }

    val favoriteCocktails = MutableLiveData<List<Cocktail>>()
    val error = MutableLiveData<String>()
    var job: Job? = null

    fun getFavoriteCocktails() {
        coroutineScope.launch {
            val list = db.getAllFavoriteCocktails()
            if (list != null) {
                withContext(Dispatchers.Main) {
                    favoriteCocktails.value = list
                }
            } else {
                withContext(Dispatchers.Main) {
                    error.value = "Something went wrong."
                }
            }
        }
    }

    fun deleteCocktail(cocktail: Cocktail) {
        coroutineScope.launch {
            val deletedRowNumber = db.deleteCocktail(cocktail)
            if (deletedRowNumber > 0) {
                val list = db.getAllFavoriteCocktails()
                if (list != null) {
                    withContext(Dispatchers.Main) {
                        favoriteCocktails.value = list
                    }
                }
            }
            else
                withContext(Dispatchers.Main){
                    error.value= "Something went wrong."
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}