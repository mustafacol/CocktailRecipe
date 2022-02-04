package com.mustafacol.coctailrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.repository.CocktailsRepository

class ViewModelFactory(val repository: CocktailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CocktailsRepository::class.java)
            .newInstance(repository)
    }


}