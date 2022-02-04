package com.mustafacol.coctailrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.model.BaseDrink
import de.hdodenhof.circleimageview.CircleImageView

class CocktailsRecyclerViewAdapter(private val drinkList: MutableList<BaseDrink>) :
    RecyclerView.Adapter<CocktailsRecyclerViewAdapter.CocktailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_cocktail, parent, false)

        return CocktailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        holder.bindView(drinkList[position])
    }

    override fun getItemCount() = drinkList.size


    class CocktailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cocktailImage: CircleImageView =
            itemView.findViewById(R.id.cocktail_image)
        private val cocktailName: TextView = itemView.findViewById(R.id.cocktail_name)
        private val cocktailDescription: TextView = itemView.findViewById(R.id.cocktail_instructions)


        fun bindView(drinkItem: BaseDrink) {
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

