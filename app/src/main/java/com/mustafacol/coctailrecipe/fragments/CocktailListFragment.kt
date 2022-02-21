package com.mustafacol.coctailrecipe.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.SwipeToDeleteCallback
import com.mustafacol.coctailrecipe.adapter.CocktailsRecyclerViewAdapter
import com.mustafacol.coctailrecipe.adapter.FavoriteCocktailRecyclerViewAdapter
import com.mustafacol.coctailrecipe.databinding.CocktailListFragmentBinding
import com.mustafacol.coctailrecipe.model.BaseDrink
import com.mustafacol.coctailrecipe.model.alphabet
import com.mustafacol.coctailrecipe.viewmodel.CocktailListViewModel

class CocktailListFragment : Fragment() {

    private lateinit var viewModel: CocktailListViewModel
    private var cocktailDrinkList: MutableList<BaseDrink> = mutableListOf()
    private var oldList: MutableList<BaseDrink> = mutableListOf()
    private var _binding: CocktailListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var pageIndex = 0
    private var isSearching = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
                if (dy > 0 && !isSearching ) {
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


        val swipeHandler = object : SwipeToDeleteCallback(requireContext(), ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = cocktailRecyclerView.adapter as CocktailsRecyclerViewAdapter
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(cocktailRecyclerView)

    }

    private fun setupObservers() {
        viewModel.drinkList.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (isSearching) {
                    cocktailDrinkList.clear()
                    cocktailDrinkList.addAll(0, it.drinks)
                } else cocktailDrinkList.addAll(it.drinks)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        var searchItem = menu.findItem(R.id.action_search)

        var searchView = searchItem.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {

                s?.let {
                    if (it.isEmpty()) {
                        viewModel.fetchCocktailByFirstLetter(alphabet[pageIndex])
                        isSearching = false
                    } else {
                        isSearching = true
                        viewModel.fetchCocktailsWithName(s)
                    }

                }
                return false
            }

        })
    }


}