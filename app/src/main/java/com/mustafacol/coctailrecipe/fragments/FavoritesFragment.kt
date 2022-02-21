package com.mustafacol.coctailrecipe.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafacol.coctailrecipe.SwipeToDeleteCallback
import com.mustafacol.coctailrecipe.adapter.FavoriteCocktailRecyclerViewAdapter
import com.mustafacol.coctailrecipe.databinding.FavoritesFragmentBinding
import com.mustafacol.coctailrecipe.model.Cocktail
import com.mustafacol.coctailrecipe.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!
    private var favoriteList: MutableList<Cocktail> = mutableListOf()
    private lateinit var favoriteRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        val root = binding.root
        favoriteRecyclerView = binding.favoriteCocktailRecyclerview
        setViewModel()
        setRecyclerView()
        viewModel.getFavoriteCocktails()
        setupObservers()

        //viewModel.getFavoriteCocktails()

        return root
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

    }

    private fun setRecyclerView() {
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoriteRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = FavoriteCocktailRecyclerViewAdapter(favoriteList, viewModel)
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext(),ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = favoriteRecyclerView.adapter as FavoriteCocktailRecyclerViewAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(favoriteRecyclerView)
    }

    private fun setupObservers() {
        viewModel.favoriteCocktails.observe(viewLifecycleOwner, Observer {

            if (viewModel.firstTimeLoad) {
                favoriteList.addAll(it)
                favoriteRecyclerView.adapter?.notifyDataSetChanged()
            }

        })


    }

    fun longPressCallBack(cocktail: Cocktail) {
        //viewModel.deleteCocktail(cocktail)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setMessage("Do you want to delete this cocktail from favorites?")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteCocktail(cocktail)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


}