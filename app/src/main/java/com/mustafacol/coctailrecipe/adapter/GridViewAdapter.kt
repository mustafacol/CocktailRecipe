package com.mustafacol.coctailrecipe.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.CocktailIngredient

class GridViewAdapter(
    private val ingredientMap: MutableMap<String, CocktailIngredient>,
    private val context: Context
) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var ingredientName: TextView
    private lateinit var ingredientMeasure: TextView
    private lateinit var ingredientImgView: ImageView

    override fun getCount(): Int {
        return ingredientMap.size
    }

    override fun getItem(p0: Int): Any? {
        return ingredientMap[p0.toString()]
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
            view = layoutInflater!!.inflate(R.layout.cell_ingredient, null)
        }

        ingredientName = view!!.findViewById(R.id.ingredient_name)
        ingredientMeasure = view!!.findViewById(R.id.ingredient_measure)
        ingredientImgView = view!!.findViewById(R.id.ingredient_img)

        ingredientName.text = ingredientMap[(position+1).toString()]?.ingredientName
        ingredientMeasure.text=ingredientMap[(position+1).toString()]?.ingredientMeasure
        val imageUrl = ingredientMap[(position+1).toString()]?.ingredientImgUrl?.replace(" ","%20")
        Glide
            .with(view)
            .load(imageUrl)
            .into(ingredientImgView)

        return view
    }
}