package com.mustafacol.coctailrecipe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.adapter.CocktailsRecyclerViewAdapter
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.Drinks
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var cocktailRecyclerView: RecyclerView
    var cocktailDrinkList: MutableList<BaseDrink> = mutableListOf()
    private lateinit var cocktailRecyclerViewAdapter: CocktailsRecyclerViewAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setRecyclerView()

        val retService = RetrofitInstance.getRetrofitInstance()
            .create(CocktailService::class.java)


        val responseLiveData: LiveData<Response<Drinks>> = liveData {
            val response = retService.getCocktailByName("margarita")
            emit(response)
        }



        responseLiveData.observe(this, Observer {
            if (it.isSuccessful) {
                val responseDrinks = it.body()?.drinks

                cocktailDrinkList.addAll(responseDrinks?.toMutableList()!!)
                cocktailRecyclerViewAdapter.notifyDataSetChanged()
            }

        })


    }

    private fun setRecyclerView() {
        cocktailRecyclerView = findViewById(R.id.coctailRecyclerView)
        cocktailRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        cocktailRecyclerViewAdapter = CocktailsRecyclerViewAdapter(cocktailDrinkList)

        cocktailRecyclerView.adapter = cocktailRecyclerViewAdapter

    }
}