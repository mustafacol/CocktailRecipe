package com.mustafacol.coctailrecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.darkColorList
import de.hdodenhof.circleimageview.CircleImageView

class CocktailsRecyclerViewAdapter(
    val context: Context,
    private val drinkList: MutableList<BaseDrink>
) :
    RecyclerView.Adapter<CocktailsRecyclerViewAdapter.CocktailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_cocktail, parent, false)

        return CocktailsViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        holder.bindView(drinkList[position],position)
    }

    override fun getItemCount() = drinkList.size


    class CocktailsViewHolder(itemView: View, val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        private val cocktailImage: CircleImageView =
            itemView.findViewById(R.id.cocktail_image)
        private val cocktailName: TextView = itemView.findViewById(R.id.cocktail_name)
        private val cocktailDescription: TextView =
            itemView.findViewById(R.id.cocktail_instructions)
        private val container: LinearLayout = itemView.findViewById(R.id.cocktail_container)

        fun bindView(drinkItem: BaseDrink, position: Int) {

            val colorPosition = position % 6
            container.setBackgroundColor(ContextCompat.getColor(context, darkColorList[colorPosition]))
            Glide
                .with(itemView)
                .load(drinkItem.strDrinkThumb)
                .into(cocktailImage)
            cocktailName.text = drinkItem.strDrink
            cocktailDescription.text = drinkItem.strInstructions

            itemView.setOnClickListener {
                val bundle = bundleOf("drinkDetail" to drinkItem)
                itemView.findNavController().navigate(
                    R.id.action_navigation_cocktailList_to_navigation_cocktail_detail,
                    bundle
                )
            }


        }
    }

}

