package com.mustafacol.coctailrecipe.ui.cocktailList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.adapter.CocktailsRecyclerViewAdapter
import com.mustafacol.coctailrecipe.databinding.CocktailListFragmentBinding
import com.mustafacol.coctailrecipe.model.BaseDrink

class CocktailListFragment : Fragment() {

    private lateinit var viewModel: CocktailListViewModel
    private var cocktailDrinkList: MutableList<BaseDrink> = mutableListOf()
    private var _binding: CocktailListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CocktailListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cocktailRecyclerView = binding.cocktailRecyclerView




        viewModel = ViewModelProvider(this)[CocktailListViewModel::class.java]
        setRecyclerView()
        viewModel.fetchCocktailsWithName()
        setupObservers()

        return root
    }


    private fun setRecyclerView() {
        cocktailRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CocktailsRecyclerViewAdapter(cocktailDrinkList)
        }

    }

    private fun setupObservers() {
        viewModel.drinkList.observe(viewLifecycleOwner, Observer {
            it?.let {
                cocktailDrinkList.addAll(it.drinks)
                cocktailRecyclerView.adapter?.notifyDataSetChanged()
            }
        })

    }

}