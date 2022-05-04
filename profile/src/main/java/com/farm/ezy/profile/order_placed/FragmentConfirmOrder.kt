package com.farm.ezy.profile.order_placed

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farm.ezy.core.models.address.AddressSet
import com.farm.ezy.core.models.items.ItemSet
import com.farm.ezy.core.models.order.OrdersSet
import com.farm.ezy.core.utils.convertLongToTime
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.profile.R
import com.farm.ezy.profile.address.FragmentAddressDirections
import com.farm.ezy.profile.databinding.FragmentConfirmOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentConfirmOrder : Fragment(R.layout.fragment_confirm_order) {

    private val binding: FragmentConfirmOrderBinding by viewBinding()
    private val args: FragmentConfirmOrderArgs by navArgs()

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
    }
//    private fun placeOrder(item: ItemSet, address: AddressSet) {
//        val ref = db.collection("Users").document(viewModel.uid!!)
//            .collection("orders")
//        val path = ref.document().id
//        ref.document(path).set(order).addOnSuccessListener {
//            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
//
//            findNavController().navigate(
//                FragmentAddressDirections.actionFragmentAddressToFragmentOrderPlaced(
//                    order
//                )
//            )
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
//        }
//    }
}