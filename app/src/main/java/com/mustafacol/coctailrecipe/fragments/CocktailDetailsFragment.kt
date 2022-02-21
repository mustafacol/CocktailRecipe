package com.mustafacol.coctailrecipe.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.ViewModelFactory
import com.mustafacol.coctailrecipe.adapter.GridViewAdapter
import com.mustafacol.coctailrecipe.adapter.InstructionRecyclerviewAdapter
import com.mustafacol.coctailrecipe.databinding.CocktallDetailsFragmentBinding
import com.mustafacol.coctailrecipe.db.CocktailsDatabase
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import com.mustafacol.coctailrecipe.viewmodel.CocktailDetailsViewModel

class CocktailDetailsFragment : Fragment() {


    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var baseDrink: BaseDrink
    private lateinit var gridView: GridView
    private var _binding: CocktallDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseDrink = arguments?.get("drinkDetail") as BaseDrink
        _binding = CocktallDetailsFragmentBinding.inflate(inflater, container, false)

        val dao =
            CocktailsDatabase.databaseInstance(requireContext().applicationContext).cocktailsDAO
        val repository = CocktailsRepository(dao)
        val viewModelFactory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[CocktailDetailsViewModel::class.java]

        viewModel.setCocktailDetails(baseDrink)
        viewModel.setIngredientMap()
        setUI()
        setupObservers()

        return binding.root
    }

    private fun setUI() {
        val instructionList = viewModel.getIngredientList()

        val instructionAdapter = InstructionRecyclerviewAdapter(instructionList)
        binding.cocktailDetailName.text = baseDrink.strDrink
        binding.cocktailDetailGlassType.text = baseDrink.strGlass
        binding.cocktailDetailCategoryType.text = baseDrink.strCategory
        binding.cocktailDetailAlcoholicType.text = baseDrink.strAlcoholic

        binding.cocktailDetailInstructionRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = instructionAdapter
        }

        binding.coctailDetailFavoriteBtn.setOnClickListener {
            viewModel.favoriteButtonClick()
        }

        Glide.with(binding.root)
            .load(baseDrink.strDrinkThumb)
            .into(binding.cocktailDetailImage)

    }

    private fun setupObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner, {
            if (it.equals("Success"))
                Toast.makeText(activity, "Cocktail is added to favorite list.", Toast.LENGTH_LONG)
                    .show()
        })

        viewModel.ingredientMap.observe(viewLifecycleOwner, Observer {
            val gridViewAdapter =
                GridViewAdapter(viewModel.ingredientMap.value?.toMutableMap()!!, requireContext())
            binding.cocktailDetailIngredientGridView.adapter = gridViewAdapter


        })
    }




}