package com.mustafacol.coctailrecipe.service

import com.google.gson.JsonObject
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.BasicCocktailResponse
import com.mustafacol.coctailrecipe.model.Drinks
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") name: String): Response<Drinks>

    @GET("search.php")
    suspend fun getCocktailByFirstLetter(@Query("f") letter: String): Response<Drinks>

    @GET("random.php")
    suspend fun getRandomCocktail(): Response<Drinks>

    @GET("filter.php")
    suspend fun getCocktailByIngredient(@Query("i") letter: String): Response<BasicCocktailResponse?>

    @GET("lookup.php")
    suspend fun getCocktailsById(@Query("i") id: String): Response<Drinks>
}