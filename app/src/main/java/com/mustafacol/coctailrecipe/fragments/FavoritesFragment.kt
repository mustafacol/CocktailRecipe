package com.mustafacol.coctailrecipe.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.ViewModelFactory
import com.mustafacol.coctailrecipe.adapter.FavoriteCocktailsGridViewAdapter
import com.mustafacol.coctailrecipe.databinding.FavoritesFragmentBinding
import com.mustafacol.coctailrecipe.db.CocktailsDatabase
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.model.emptyBaseDrink
import com.mustafacol.coctailrecipe.repository.CocktailsRepository
import com.mustafacol.coctailrecipe.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!
    private var favoriteList: List<Cocktail> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        setViewModel()
        viewModel.getFavoriteCocktails()
        setupObservers()

        //viewModel.getFavoriteCocktails()

        return root
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

    }


    private fun setupObservers() {
        viewModel.favoriteCocktails.observe(viewLifecycleOwner, Observer {
            val favoriteAdapter = FavoriteCocktailsGridViewAdapter(
                requireContext(),
                it,
                ::longPressCallBack
            )
            binding.favoriteCocktailsGridView.adapter = favoriteAdapter
        })


    }

    fun longPressCallBack(cocktail: Cocktail) {
        viewModel.deleteCocktail(cocktail)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.long_press_menu, menu)
    }




}