package com.mustafacol.coctailrecipe.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.ViewModelFactory
import com.mustafacol.coctailrecipe.adapter.GridViewAdapter
import com.mustafacol.coctailrecipe.adapter.InstructionRecyclerviewAdapter
import com.mustafacol.coctailrecipe.databinding.FavoritesFragmentBinding
import com.mustafacol.coctailrecipe.databinding.RandomCocktailsFragmentBinding
import com.mustafacol.coctailrecipe.db.CocktailsDatabase
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.CocktailIngredient
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import com.mustafacol.coctailrecipe.viewmodel.CocktailDetailsViewModel
import com.mustafacol.coctailrecipe.viewmodel.RandomCocktailsViewModel

class RandomCocktailsFragment : Fragment() {

    private lateinit var viewModel: CocktailDetailsViewModel
    private var _binding: RandomCocktailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseDrink: BaseDrink

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (_binding == null)
            _binding = RandomCocktailsFragmentBinding.inflate(inflater, container, false)

        val root = binding.root

        setViewModel()
        viewModel.getRandomCocktails()
        setupObservers()

        return root
    }

    private fun setViewModel() {
        val dao =
            CocktailsDatabase.databaseInstance(requireContext().applicationContext).cocktailsDAO
        val repository = CocktailsRepository(dao)
        val viewModelFactory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[CocktailDetailsViewModel::class.java]
    }

    private fun setUi(baseDrink: BaseDrink) {
        val instructionList = viewModel.getIngredientList()

        val instructionAdapter = InstructionRecyclerviewAdapter(instructionList)

        binding.cocktailRandomName.text = baseDrink.strDrink
        binding.cocktailRandomGlassType.text = baseDrink.strGlass
        binding.cocktailRandomCategoryType.text = baseDrink.strCategory
        binding.cocktailRandomAlcoholicType.text = baseDrink.strAlcoholic

        binding.cocktailRandomInstructionRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = instructionAdapter
        }

        binding.coctailRandomFavoriteBtn.setOnClickListener {
            viewModel.favoriteButtonClick()
        }

        Glide.with(binding.root)
            .load(baseDrink.strDrinkThumb)
            .into(binding.cocktailRandomImage)
    }

    private fun setupObservers() {
        viewModel.cocktailDetails.observe(viewLifecycleOwner, Observer {
            setUi(it)
        })
        viewModel.successMessage.observe(viewLifecycleOwner, {
            if (it.equals("Success"))
                Toast.makeText(activity, "Cocktail is added to favorite list.", Toast.LENGTH_LONG)
                    .show()
        })

        viewModel.ingredientMap.observe(viewLifecycleOwner, Observer {
            val gridViewAdapter =
                GridViewAdapter(viewModel.ingredientMap.value?.toMutableMap()!!, requireContext())
            binding.cocktailRandomIngredientGridView.adapter = gridViewAdapter
        })
    }




}