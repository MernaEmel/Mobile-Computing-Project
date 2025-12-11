package com.example.brewbuddy.presentation.screens.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brewbuddy.R
import com.example.brewbuddy.data.local.database.entities.OrderHistory
import com.example.brewbuddy.databinding.FragmentOrderBinding
import com.example.brewbuddy.presentation.viewmodels.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.geeksforgeeks.demo.Adapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private val viewModel: OrderHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupSegmentButton()
    }

    private fun setupSegmentButton() {
        binding.segmentGroup.check(R.id.btn_recent)

        binding.segmentGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_recent -> viewModel.loadRecentOrders()
                    R.id.btn_past -> viewModel.loadPastOrders()
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect { orders ->
                adapter.updateList(orders)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = Adapter(emptyList())
        binding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@OrderFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = OrderFragment()
    }
}