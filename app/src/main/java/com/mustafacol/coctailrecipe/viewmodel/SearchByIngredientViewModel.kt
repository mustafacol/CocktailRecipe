package com.mustafacol.coctailrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mustafacol.coctailrecipe.model.BasicCocktail
import com.mustafacol.coctailrecipe.model.BasicCocktailResponse
import com.mustafacol.coctailrecipe.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class SearchByIngredientViewModel : ViewModel() {

    private val retService = RetrofitInstance.retrofitInstance
    var basicCocktailList = MutableLiveData<List<BasicCocktail>>()


    fun getCocktailByIngredient(ingredient: String) {

        viewModelScope.launch {
            var response = retService.getCocktailByIngredient(ingredient)


        }
    }
}