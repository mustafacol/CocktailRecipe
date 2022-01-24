package com.mustafacol.coctailrecipe

import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Drinks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php?")
    suspend fun getCocktailByName(@Query("s") name: String): Response<Drinks>
}