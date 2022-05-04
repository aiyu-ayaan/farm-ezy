package com.farm.ezy.profile.order_placed

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farm.ezy.core.models.order.OrdersSet
import com.farm.ezy.core.utils.convertLongToTime
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentConfirmOrderBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class FragmentConfirmOrder : Fragment(R.layout.fragment_confirm_order) {

    private val binding: FragmentConfirmOrderBinding by viewBinding()
    private val args: FragmentConfirmOrderArgs by navArgs()

    @Inject
    lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView(args.order)

    }

    private fun setView(order: OrdersSet) = binding.rowOrder.apply {
        relativeLayoutContent.isVisible = true
        textViewItemName.text = order.name
        textViewDate.text =
            binding.root.context.resources.getString(
                com.farm.ezy.core.R.string.delivered_at,
                order.buyOn.convertLongToTime()
            )
        textViewPrice.text =
            binding.root.context.resources.getString(
                com.farm.ezy.core.R.string.money,
                order.price
            )
        textViewAddress.text =
            binding.root.context.resources.getString(
                com.farm.ezy.core.R.string.delivered_to,
                order.location
            )
        textViewQuantity.text =
            binding.root.context.resources.getString(
                com.farm.ezy.core.R.string.quantity,
                order.quantity.toString()
            )
        order.imageLink.loadImageDefault(
            binding.root,
            binding.rowOrder.imageViewItem,
            binding.rowOrder.progressBarRowOrders
        )
        textViewShowDetail.isVisible = false
        textViewPaymentType.text = binding.root.context.resources.getString(
            com.farm.ezy.core.R.string.payment_type,
            order.payment
        )
        binding.buttonConfirmOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() = lifecycleScope.launchWhenStarted {
        binding.progressBarBuyOrder.isVisible = true
        delay(1000)
        val ref = db.collection("Users").document(args.uid)
            .collection("orders")
        val path = ref.document().id
        ref.document(path).set(args.order).addOnSuccessListener {
            runBlocking {
                binding.progressBarBuyOrder.isVisible = false
                delay(500)
            }
            Toast.makeText(
                requireContext(), resources.getString(
                    com.farm.ezy.core.R.string.order_placed,
                ), Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()

        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}