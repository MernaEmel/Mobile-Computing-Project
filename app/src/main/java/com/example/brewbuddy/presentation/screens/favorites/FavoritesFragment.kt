package com.example.brewbuddy.presentation.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.brewbuddy.databinding.FragmentFavoritesBinding
import com.example.brewbuddy.databinding.ItemDrinkBinding
import com.example.brewbuddy.domain.model.Favorite

import com.example.recipebox.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = FavoritesAdapter { fav ->
            viewModel.deleteFavorite(fav)
        }

        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = this@FavoritesFragment.adapter
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favorites.collectLatest { list ->
                adapter.submitList(list)
                binding.rvFavorites.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
            }
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                state.errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                state.successMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class FavoritesAdapter(
        private val onDelete: (Favorite) -> Unit
    ) : androidx.recyclerview.widget.ListAdapter<Favorite, FavoritesAdapter.FavViewHolder>(
        object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) =
                oldItem == newItem
        }
    ) {

        inner class FavViewHolder(val binding: ItemDrinkBinding) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
            val binding = ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FavViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
            val fav = getItem(position)
            with(holder.binding) {
                tvDrinkName.text = fav.name
                tvDrinkPrice.text = "RP ${fav.priceCents}"
                ivDrinkImage.load(fav.image) { crossfade(true) }

                ivFavorite.setOnClickListener {
                    onDelete(fav)
                }
            }
        }
    }
}
