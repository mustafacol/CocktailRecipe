package com.mustafacol.coctailrecipe.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.Cocktail

class FavoriteCocktailsGridViewAdapter(
    private val context: Context,
    private val favoriteCocktails: List<Cocktail>,
    private val longPressedCallback: (Cocktail) -> Unit
) : BaseAdapter(){
    private var layoutInflater: LayoutInflater? = null
    private lateinit var cocktailName: TextView
    private lateinit var cocktailFrame: ImageView
    override fun getCount(): Int {
        return favoriteCocktails.size
    }

    override fun getItem(p0: Int): Any {
        return favoriteCocktails[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (view == null) {
            view = layoutInflater!!.inflate(R.layout.cell_favorite, null)
        }


        view?.setOnLongClickListener {
            longPressedCallback(favoriteCocktails[position])
            return@setOnLongClickListener true
        }

        cocktailFrame = view!!.findViewById(R.id.cell_favorite_cocktailImage)
        cocktailName = view!!.findViewById(R.id.cell_favorite_cocktailName)

        cocktailName.text = favoriteCocktails[position].name
        val imageUrl = favoriteCocktails[position].imageUrl
        Glide
            .with(context)
            .load(imageUrl)
            .into(cocktailFrame)


        return view

    }



}