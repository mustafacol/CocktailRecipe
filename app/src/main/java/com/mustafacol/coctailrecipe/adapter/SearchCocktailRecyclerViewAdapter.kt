package com.mustafacol.coctailrecipe.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.BasicCocktail
import com.mustafacol.coctailrecipe.model.colorList
import com.mustafacol.coctailrecipe.model.darkColorList
import com.mustafacol.coctailrecipe.viewmodel.SearchByIngredientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class SearchCocktailRecyclerViewAdapter(
    private val cocktailList: MutableList<BasicCocktail>,
    private val viewModel: SearchByIngredientViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<SearchCocktailRecyclerViewAdapter.SearchCocktailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCocktailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_recycler_favorite, parent, false)


        return SearchCocktailViewHolder(view, viewModel, context)
    }

    override fun onBindViewHolder(holder: SearchCocktailViewHolder, position: Int) {
        holder.bindView(cocktailList[position], position)
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }


    class SearchCocktailViewHolder(
        itemView: View,
        val viewModel: SearchByIngredientViewModel,
        val context: Context
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView =
            itemView.findViewById(R.id.cell_fav_cocktailImage)
        private val cocktailName: TextView = itemView.findViewById(R.id.cell_fav_cocktailName)
        private val favImage: ImageView = itemView.findViewById(R.id.cell_fav_image)
        private val layout: RelativeLayout = itemView.findViewById(R.id.cell_fav_layout)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.favorite_progressbar)


        fun bindView(basicCocktail: BasicCocktail, position: Int) {
            progressBar.visibility = View.VISIBLE
            layout.visibility = View.GONE
            favImage.visibility = View.GONE

            val colorPosition = position % 6

            layout.setBackgroundColor(ContextCompat.getColor(context, colorList[colorPosition]))

            Glide
                .with(itemView)
                .load(basicCocktail.strDrinkThumb)
                .into(cocktailImage)

            itemView.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getCocktailsById(basicCocktail.idDrink)


                    withContext(Dispatchers.Main) {
                        if (viewModel.clickedCocktail.value != null) {
                            val bundle = bundleOf("drinkDetail" to viewModel.clickedCocktail.value)
                            itemView.findNavController().navigate(
                                R.id.action_navigation_search_by_ingredient_to_navigation_cocktail_detail,
                                bundle
                            )
                        }

                    }
                }
            }

            cocktailName.text = basicCocktail.strDrink

            progressBar.visibility = View.GONE
            layout.visibility = View.VISIBLE
        }
    }

}