package com.mustafacol.coctailrecipe.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.BasicCocktail
import java.net.URL

class SearchCocktailRecyclerViewAdapter(
    private val cocktailList: MutableList<BasicCocktail>
) :
    RecyclerView.Adapter<SearchCocktailRecyclerViewAdapter.SearchCocktailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCocktailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_recycler_favorite, parent, false)
        return SearchCocktailViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCocktailViewHolder, position: Int) {
        holder.bindView(cocktailList[position])
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }


    class SearchCocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView =
            itemView.findViewById(R.id.cell_fav_cocktailImage)
        private val cocktailName: TextView = itemView.findViewById(R.id.cell_fav_cocktailName)
        private val favImage: ImageView = itemView.findViewById(R.id.cell_fav_image)
        private val layout: RelativeLayout = itemView.findViewById(R.id.cell_fav_layout)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.favorite_progressbar)

        fun bindView(basicCocktail: BasicCocktail) {
            progressBar.visibility = View.VISIBLE
            layout.visibility = View.GONE
            favImage.visibility = View.GONE

            Glide
                .with(itemView)
                .load(basicCocktail.strDrinkThumb)
                .into(cocktailImage)

            cocktailName.text = basicCocktail.strDrink

            progressBar.visibility = View.GONE
            layout.visibility = View.VISIBLE
        }
    }

}