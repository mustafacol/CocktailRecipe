package com.mustafacol.coctailrecipe.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mustafacol.coctailrecipe.R
import com.mustafacol.coctailrecipe.viewmodel.RandomCocktailsViewModel

class RandomCocktailsFragment : Fragment() {

    private lateinit var viewModel: RandomCocktailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RandomCocktailsViewModel::class.java)

        return inflater.inflate(R.layout.random_cocktails_fragment, container, false)
    }


}