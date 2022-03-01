package com.mustafacol.coctailrecipe.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.adapter.SearchCocktailRecyclerViewAdapter
import com.mustafacol.coctailrecipe.databinding.FragmentSearchByIngredientBinding
import com.mustafacol.coctailrecipe.databinding.RandomCocktailsFragmentBinding
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.BasicCocktail
import com.mustafacol.coctailrecipe.viewmodel.CocktailDetailsViewModel
import com.mustafacol.coctailrecipe.viewmodel.SearchByIngredientViewModel


class SearchByIngredientFragment : Fragment() {
    private lateinit var viewModel: SearchByIngredientViewModel
    private var _binding: FragmentSearchByIngredientBinding? = null
    private val binding get() = _binding!!

    private var cocktailDrinkList: MutableList<BasicCocktail> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchByIngredientBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel = ViewModelProvider(this)[SearchByIngredientViewModel::class.java]

        setUi()

        setupObservers()

        return root
    }

    private fun setUi() {
        setRecyclerView()


        editText = binding.searchEditTxt

        editText.addTextChangedListener {
            if (it?.length!! > 1) {
                viewModel.getCocktailByIngredient(it.toString())

            } else {
                cocktailDrinkList.clear()
                recyclerView.adapter?.notifyDataSetChanged()

            }
        }
    }

    private fun setRecyclerView() {
        recyclerView = binding.searchByIngredientRecyclerView
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = SearchCocktailRecyclerViewAdapter(cocktailDrinkList, viewModel, context)
        }
    }

    private fun setupObservers() {
        viewModel.basicCocktailList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cocktailDrinkList.addAll(it)
                recyclerView.adapter?.notifyDataSetChanged()
            }

        })
    }


}