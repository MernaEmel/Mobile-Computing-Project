package com.example.brewbuddy.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.Toast

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.brewbuddy.R
import com.example.brewbuddy.data.repository.impl.UserRepositoryImpl
import com.example.brewbuddy.databinding.FragmentMainBinding
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import com.example.brewbuddy.presentation.screens.details_screen.DetailsFragment
import com.example.brewbuddy.presentation.screens.menu.DrinkMenuFragment
import com.example.brewbuddy.presentation.screens.favorites.FavoritesFragment
import com.example.brewbuddy.presentation.screens.home.HomeFragment
import com.example.brewbuddy.presentation.screens.order.OrderFragment
import com.example.brewbuddy.presentation.viewmodel.EnterNameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    private val viewModel: EnterNameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        /// val sharedPref = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE)
//        val repository = UserRepositoryImpl(requireContext())
//        val saveNameUseCase = SaveUserNameUseCase(repository)
//        val getNameUseCase = GetUserNameUseCase(repository)
//        val deleteUserNameUseCase = DeleteUserNameUseCase(repository)
//        viewModel = EnterNameViewModel(saveNameUseCase, getNameUseCase, deleteUserNameUseCase)
//        viewModel.getName()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userName.collect { name ->
                    val displayName = name ?: "Guest"
                    binding.tvMainAppBarTitle.text = "Good day, $displayName"
                }
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(binding.mainAppBar) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                systemBars.top,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigation) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                systemBars.bottom
            )
            insets
        }
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container_view, HomeFragment())
                .commit()
        }


        val topAppBar = binding.mainAppBar
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.main_menu_logoutBtn -> {
                    viewModel.logout()
                    Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.toEnterNameFragment)
                    true
                }

                else -> {
                    Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
        val bottomBar = binding.bottomNavigation
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_nav_bar_item -> {
                    val getName = viewModel.userName.value ?: "Guest"
                    binding.tvMainAppBarTitle.text = "Good day, $getName"
                    childFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container_view, HomeFragment())
                        .commit()
                    true
                }


                R.id.drink_menu_nav_bar_item -> {
                    binding.tvMainAppBarTitle.text = "What would you like to drink today?"
                    childFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container_view, DrinkMenuFragment())
                        .commit()
                    true
                }

                R.id.orders_nav_bar_item -> {
                    binding.tvMainAppBarTitle.text = "Your orders"
                    childFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container_view, OrderFragment())
                        .commit()
                    true
                }

                R.id.favorites_nav_bar_item -> {
                    binding.tvMainAppBarTitle.text = "Your favorite drinks to lighten up your day"
                    childFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container_view, FavoritesFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Logout is handled in topAppBar.setOnMenuItemClickListener above
        return super.onOptionsItemSelected(item)
    }
}