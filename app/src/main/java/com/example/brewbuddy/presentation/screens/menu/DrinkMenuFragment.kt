package com.example.brewbuddy.presentation.screens.menu

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.ui.graphics.Color
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewbuddy.CoffeeAdapter
import com.example.brewbuddy.R
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import com.example.brewbuddy.presentation.viewmodel.CoffeeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DrinkMenuFragment : Fragment(R.layout.fragment_drink_menu) {

    private val viewModel: CoffeeViewModel by viewModels()
    private lateinit var adapter: CoffeeAdapter
    private var currentSelectedCategory = "COLD"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.coffeeRecyclerView)
        adapter = CoffeeAdapter(emptyList()) { coffeeEntity ->
            val coffee = Coffee(
                id = coffeeEntity.id,
                title = coffeeEntity.title,
                description = coffeeEntity.description,
                ingredients = coffeeEntity.ingredients.split(",").map { it.trim() },
                image = coffeeEntity.imageUrl,
                price = coffeeEntity.price,
                category = if (coffeeEntity.category == "HOT") CoffeeCategory.HOT else CoffeeCategory.COLD,
                isFavorite = coffeeEntity.isFavorite
            )
            
            val bundle = Bundle().apply {
                putParcelable("coffee", coffee)
            }
            requireActivity().findNavController(R.id.main).navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val searchBar = view.findViewById<EditText>(R.id.searchBar)
        searchBar.addTextChangedListener { viewModel.filter(it.toString()) }

        val btnHot = view.findViewById<TextView>(R.id.buttonHot)
        val btnCold = view.findViewById<TextView>(R.id.buttonCold)
        val coldContainer = view.findViewById<CardView>(R.id.coldButtonContainer)
        val hotContainer = view.findViewById<CardView>(R.id.hotButtonContainer)

        viewModel.filterByCategory(currentSelectedCategory)
        updateCategoryButtons(btnCold, btnHot, coldContainer, hotContainer)

        btnHot.setOnClickListener { 
            currentSelectedCategory = "HOT"
            viewModel.filterByCategory("HOT")
            updateCategoryButtons(btnCold, btnHot, coldContainer, hotContainer)
        }
        btnCold.setOnClickListener { 
            currentSelectedCategory = "COLD"
            viewModel.filterByCategory("COLD") 
            updateCategoryButtons(btnCold, btnHot, coldContainer, hotContainer)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.coffeeList.collectLatest { adapter.updateList(it) }
        }
    }
    
    private fun updateCategoryButtons(btnCold: TextView, btnHot: TextView, coldContainer: CardView, hotContainer: CardView) {
        if (currentSelectedCategory == "COLD") {
            coldContainer.setCardBackgroundColor(resources.getColor(R.color.secondary , null))
            btnCold.setTextColor(resources.getColor(android.R.color.white, null))
            hotContainer.setCardBackgroundColor(resources.getColor(android.R.color.transparent, null))
            btnHot.setTextColor(resources.getColor(R.color.secondary , null))
        } else {
            hotContainer.setCardBackgroundColor(resources.getColor(R.color.secondary, null))
            btnHot.setTextColor(resources.getColor(android.R.color.white, null))
            coldContainer.setCardBackgroundColor(resources.getColor(android.R.color.transparent, null))
            btnCold.setTextColor(resources.getColor(R.color.secondary, null))
        }
    }
}
