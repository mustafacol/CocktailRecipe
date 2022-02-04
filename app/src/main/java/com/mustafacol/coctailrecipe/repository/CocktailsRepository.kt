package com.mustafacol.coctailrecipe.repository

import androidx.lifecycle.LiveData
import com.mustafacol.coctailrecipe.db.CocktailsDAO
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail

class CocktailsRepository(private val dao: CocktailsDAO) {

    suspend fun insert(cocktail: Cocktail):Long {
        return dao.insertCocktail(cocktail)
    }

    suspend fun delete(cocktail:Cocktail):Int{
        return dao.deleteCocktail(cocktail)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    fun getAllFavoriteCocktails(): List<Cocktail> {
        return dao.getAllFavoriteCocktails()
    }
}