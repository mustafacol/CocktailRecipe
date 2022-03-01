package com.mustafacol.coctailrecipe.retrofit

import com.google.gson.GsonBuilder
import com.mustafacol.coctailrecipe.NullOnEmptyConverterFactory
import com.mustafacol.coctailrecipe.service.CocktailService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

            private fun retrofit(): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .build()
            }

        val retrofitInstance: CocktailService by lazy {
            retrofit().create(CocktailService::class.java)
        }
    }
}