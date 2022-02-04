package com.mustafacol.coctailrecipe.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Cocktail

@Dao
interface CocktailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: Cocktail):Long

    @Delete
    suspend fun deleteCocktail(drink: Cocktail):Int

    @Query("DELETE FROM favorite_cocktails")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorite_cocktails")
    fun getAllFavoriteCocktails():List<Cocktail>
}