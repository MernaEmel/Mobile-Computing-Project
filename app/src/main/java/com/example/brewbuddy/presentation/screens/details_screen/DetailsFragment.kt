package com.example.brewbuddy.presentation.screens.details_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.brewbuddy.R
import com.example.brewbuddy.databinding.FragmentDetailsBinding
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import com.example.brewbuddy.presentation.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            binding.closeBtn.setPadding(
                binding.closeBtn.paddingLeft,
                binding.closeBtn.paddingTop + systemBars.top,
                binding.closeBtn.paddingRight,
                binding.closeBtn.paddingBottom
            )

            binding.favouriteBtn.setPadding(
                binding.favouriteBtn.paddingLeft,
                binding.favouriteBtn.paddingTop + systemBars.top,
                binding.favouriteBtn.paddingRight,
                binding.favouriteBtn.paddingBottom
            )

            insets
        }

        setupUI()
        observeViewModel()

        val coffee = args.coffee
        viewModel.setCoffee(coffee)
    }

    private fun setupUI() {
        binding.btnPlus.setOnClickListener {
            viewModel.incrementQuantity()
        }

        binding.btnMinus.setOnClickListener {
            viewModel.decrementQuantity()
        }

        binding.btnBuyNow.setOnClickListener {
            viewModel.proceedToPayment()
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.favouriteBtn.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateUI(state)
            }
        }
    }

        private fun updateUI(state: com.example.brewbuddy.presentation.viewmodel.DetailsUiState) {
        binding.tvQuantity.text = state.quantity.toString()

        state.coffee?.let { coffee ->
            binding.productTitle.text = coffee.title
            binding.productDescription.text = coffee.description
            binding.productPrice.text = "Rp ${String.format("%.0f", coffee.price)}"

            Glide.with(this)
                .load(coffee.image)
                .placeholder(R.drawable.coffee_image_placeholder)
                .transition(withCrossFade(300))
                .into(binding.productImage)
        }

        binding.favouriteBtn.isSelected = state.isFavorite

        if (state.shouldNavigateToPayment) {
            navigateToPayment(state.selectedCoffee, state.finalQuantity)
            viewModel.onNavigatedToPayment()
        }
    }

    private fun navigateToPayment(coffee: Coffee?, quantity: Int) {
        coffee?.let {
            try {
                val action = DetailsFragmentDirections.actionDetailsFragmentToPaymentFragment(
                    coffeeId = it.id,
                    coffeeName = it.title,
                    coffeePrice = it.price.toFloat(),
                    quantity = quantity,
                    totalAmount = (it.price * quantity).toFloat(),
                    coffeeImage = it.image
                )
                findNavController().navigate(action)
            } catch (e: Exception) {
                // Fallback navigation using bundle if directions fail
                val bundle = Bundle().apply {
                    putInt("coffeeId", it.id)
                    putString("coffeeName", it.title)
                    putFloat("coffeePrice", it.price.toFloat())
                    putInt("quantity", quantity)
                    putFloat("totalAmount", (it.price * quantity).toFloat())
                    putString("coffeeImage", it.image)
                }
                findNavController().navigate(R.id.action_detailsFragment_to_paymentFragment, bundle)
            }
        }
    }




}