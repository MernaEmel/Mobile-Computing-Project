package com.example.brewbuddy.presentation.screens.payment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.brewbuddy.R
import com.example.brewbuddy.databinding.FragmentPaymentBinding
import com.example.brewbuddy.presentation.viewmodel.PaymentViewModel
import com.example.brewbuddy.presentation.viewmodels.OrderHistoryViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()

        val coffeeId = try { args.coffeeId } catch (e: Exception) { arguments?.getInt("coffeeId") ?: 0 }
        val coffeeName = try { args.coffeeName } catch (e: Exception) { arguments?.getString("coffeeName") ?: "" }
        val coffeePrice = try { args.coffeePrice } catch (e: Exception) { arguments?.getFloat("coffeePrice") ?: 0f }
        val quantity = try { args.quantity } catch (e: Exception) { arguments?.getInt("quantity") ?: 1 }
        val totalAmount = try { args.totalAmount } catch (e: Exception) { arguments?.getFloat("totalAmount") ?: 0f }
        val imageUrl = try { args.coffeeImage } catch (e: Exception) { arguments?.getString("coffeeImage") ?: "" }

        android.util.Log.d("PaymentFragment", "Received data - ID: $coffeeId, Name: $coffeeName, Price: $coffeePrice, Quantity: $quantity, Total: $totalAmount")

        viewModel.setPaymentData(
            coffeeId = coffeeId,
            coffeeName = coffeeName,
            coffeePrice = coffeePrice.toDouble(),
            quantity = quantity,
            totalAmount = totalAmount.toDouble(),
            imageUrl = imageUrl
        )

        viewModel.loadAddress()
    }

    private fun setupUI() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPlaceOrder.setOnClickListener {
            // Check if address is entered before allowing order
            if (!viewModel.hasAddress()) {
                // Show message that address is required
                android.widget.Toast.makeText(
                    requireContext(), 
                    "Please add a delivery address before placing your order", 
                    android.widget.Toast.LENGTH_LONG
                ).show()
                // Optionally open address dialog
                showAddressDialog()
                return@setOnClickListener
            }
            
            // Proceed with existing order logic (unchanged)
            viewModel.placeOrder()
            showSuccessDialog()
        }

        binding.btnEdit.setOnClickListener {
            showAddressDialog()
        }
    }

    private fun showSuccessDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_order_success, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<View>(R.id.btnDone).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: com.example.brewbuddy.presentation.viewmodel.PaymentUiState) {
        binding.orderTitle.text = "${state.quantity}x ${state.coffeeName}"
        binding.orderPrice.text = "         Rp ${String.format("%.0f", state.coffeePrice * state.quantity)}"

        val subtotal = state.coffeePrice * state.quantity
        binding.subtotal.text = "Rp ${String.format("%.0f", subtotal)}"
        binding.deliveryFee.text = "Rp ${String.format("%.0f", state.deliveryFee)}"
        binding.packagingFee.text = "Rp ${String.format("%.0f", state.packagingFee)}"
        binding.promoDiscount.text = "Rp ${String.format("%.0f", state.promoDiscount)}"
        binding.totalPrice.text = "Rp ${String.format("%.0f", state.totalAmount)}"

        binding.tvNoAddress.text = state.deliveryAddress
        
        // Visual indication when no address is set (without affecting database operations)
        if (!viewModel.hasAddress()) {
            binding.tvNoAddress.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
            binding.btnPlaceOrder.alpha = 0.6f // Make button appear disabled
        } else {
            binding.tvNoAddress.setTextColor(resources.getColor(android.R.color.black, null))
            binding.btnPlaceOrder.alpha = 1.0f // Make button appear enabled
        }

        if (state.orderPlaced) {
            findNavController().navigateUp()
        }
    }

    private fun showAddressDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_address, null)
        
        val etStreetAddress = dialogView.findViewById<TextInputEditText>(R.id.etStreetAddress)
        val etCity = dialogView.findViewById<TextInputEditText>(R.id.etCity)
        val etNotes = dialogView.findViewById<TextInputEditText>(R.id.etNotes)
        
        if (viewModel.hasAddress()) {
            val savedAddress = viewModel.uiState.value.deliveryAddress

        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<View>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.btnSave).setOnClickListener {
            val streetAddress = etStreetAddress.text?.toString()?.trim() ?: ""
            val city = etCity.text?.toString()?.trim() ?: ""
            val notes = etNotes.text?.toString()?.trim() ?: ""

            if (streetAddress.isNotEmpty() && city.isNotEmpty()) {
                viewModel.saveAddress(streetAddress, city,  notes)
                // Refresh UI to update address display and button state
                updateUI(viewModel.uiState.value)
                dialog.dismiss()
            } else {
                if (streetAddress.isEmpty()) {
                    etStreetAddress.error = "Street address is required"
                }
                if (city.isEmpty()) {
                    etCity.error = "City is required"
                }
            }
        }

        dialog.show()
    }
}