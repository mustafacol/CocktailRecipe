package com.mustafacol.coctailrecipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.adapter.CocktailsRecyclerViewAdapter
import com.mustafacol.coctailrecipe.databinding.CocktailListFragmentBinding
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.alphabet
import com.mustafacol.coctailrecipe.viewmodel.CocktailListViewModel

class CocktailListFragment : Fragment() {

    private lateinit var viewModel: CocktailListViewModel
    private var cocktailDrinkList: MutableList<BaseDrink> = mutableListOf()
    private var _binding: CocktailListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var pageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CocktailListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cocktailRecyclerView = binding.cocktailRecyclerView
        progressBar = binding.progressBar


        viewModel = ViewModelProvider(this)[CocktailListViewModel::class.java]
        setRecyclerView()
        viewModel.fetchCocktailByFirstLetter(alphabet[pageIndex])
        setupObservers()

        return root
    }


    private fun setRecyclerView() {
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cocktailRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = CocktailsRecyclerViewAdapter(cocktailDrinkList)
        }



        cocktailRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    var visibleItemCount = mLayoutManager.childCount
                    var totalItemCount = mLayoutManager.itemCount
                    var pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()


                    if (visibleItemCount + pastVisibleItems >= totalItemCount && pageIndex < 25) {
                        pageIndex++
                        viewModel.fetchCocktailByFirstLetter(alphabet[pageIndex])
                    }
                }
            }
        })

    }

    private fun setupObservers() {
        viewModel.drinkList.observe(viewLifecycleOwner, Observer {
            it?.let {
                cocktailDrinkList.addAll(it.drinks)
                cocktailRecyclerView.adapter?.notifyDataSetChanged()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                } else
                    progressBar.visibility = View.GONE
            }
        })

    }

}