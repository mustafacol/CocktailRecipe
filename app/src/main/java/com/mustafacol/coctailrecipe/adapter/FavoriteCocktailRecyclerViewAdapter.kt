package com.mustafacol.coctailrecipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.BasicCocktail
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.model.toBitmap
import com.mustafacol.coctailrecipe.viewmodel.FavoritesViewModel
import kotlinx.coroutines.*
import java.net.URL


class FavoriteCocktailRecyclerViewAdapter(
    private val favoriteCocktails: MutableList<Cocktail>,
    private val viewModel: FavoritesViewModel
) :
    RecyclerView.Adapter<FavoriteCocktailRecyclerViewAdapter.FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_recycler_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(favoriteCocktails[position])
    }

    override fun getItemCount(): Int {
        return favoriteCocktails.size
    }

    fun removeAt(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteCocktail(favoriteCocktails[position])
            withContext(Dispatchers.Main) {
                favoriteCocktails.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    class FavoriteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView =
            itemView.findViewById(R.id.cell_fav_cocktailImage)
        private val cocktailName: TextView = itemView.findViewById(R.id.cell_fav_cocktailName)
        private val layout: RelativeLayout = itemView.findViewById(R.id.cell_fav_layout)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.favorite_progressbar)
        private var bitmap: Bitmap? = null

        @SuppressLint("ResourceAsColor")


        fun bindView(cocktail: Cocktail) {

            progressBar.visibility = View.VISIBLE
            layout.visibility = View.GONE
            val url = URL(cocktail.imageUrl)

            CoroutineScope(Dispatchers.IO).launch {
                bitmap = url.toBitmap()!!

                withContext(Dispatchers.Main) {
                    Palette.Builder(bitmap!!).generate {
                        it?.let {


                            val vibrantColor = it.dominantSwatch

                            Glide
                                .with(itemView)
                                .load(cocktail.imageUrl)
                                .into(cocktailImage)
                            cocktailName.text = cocktail.name
                            cocktailName.setTextColor(getComplementaryColor(vibrantColor?.rgb!!))
                            layout.setBackgroundColor(vibrantColor?.rgb!!)

                            progressBar.visibility = View.GONE
                            layout.visibility = View.VISIBLE
                        }
                    }
                }

            }


        }

        private fun getComplementaryColor(color: Int): Int {
            var R = color and 255
            var G = color shr 8 and 255
            var B = color shr 16 and 255
            val A = color shr 24 and 255
            R = 255 - R
            G = 255 - G
            B = 255 - B
            return R + (G shl 8) + (B shl 16) + (A shl 24)
        }

    }
}