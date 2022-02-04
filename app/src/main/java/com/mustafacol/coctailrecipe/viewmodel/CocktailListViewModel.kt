package com.mustafacol.coctailrecipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafacol.coctailrecipe.retrofit.RetrofitInstance
import com.mustafacol.coctailrecipe.model.Drinks
import kotlinx.coroutines.*

class CocktailListViewModel : ViewModel() {
    var drinkList = MutableLiveData<Drinks>()
    var drinkListLoadError = MutableLiveData<String?>()
    var loading = MutableLiveData<Boolean>()
    private val retService = RetrofitInstance.retrofitInstance
    var job: Job? = null


    fun fetchCocktailsWithName() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retService.getCocktailByName("margarita")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    drinkList.value = response.body()
                    drinkListLoadError.value = null
                    loading.value = false
                }
            }
        }
    }

    fun fetchCocktailByFirstLetter(firstLetter: String) {
        job = CoroutineScope(
            Dispatchers.IO
        ).launch {
            val response = retService.getCocktailByFirstLetter(firstLetter)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    drinkList.value = response.body()
                    drinkListLoadError.value = null
                    loading.value = false
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}