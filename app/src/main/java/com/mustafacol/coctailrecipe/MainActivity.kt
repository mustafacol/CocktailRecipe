package com.mustafacol.coctailrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Drinks
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService = RetrofitInstance.getRetrofitInstance()
            .create(CocktailService::class.java)

        val responseLiveData:LiveData<Response<Drinks>> = liveData {
            val response = retService.getCocktailByName("margarita")
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            Log.i("MYTAG",it.body().toString())
        })
    }
}