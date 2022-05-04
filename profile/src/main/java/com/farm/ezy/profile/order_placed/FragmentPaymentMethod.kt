package com.farm.ezy.profile.order_placed

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentPaymentMethodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPaymentMethod : Fragment(R.layout.fragment_payment_method) {
    private val binding: FragmentPaymentMethodBinding by viewBinding()
    private val args: FragmentPaymentMethodArgs by navArgs()
    private lateinit var paymentMethod: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentMethod = resources.getString(
            com.farm.ezy.core.R.string.cod,
        )
        binding.apply {
            textViewItemName.text = args.order.name
            textViewDate.text = args.order.location
            radioGroupPayment.checkedRadioButtonId
            setUpRadioButton()
            confirmOrder()
        }
    }

    private fun confirmOrder() = binding.buttonProceed.apply {
        setOnClickListener {
            findNavController().navigate(
                FragmentPaymentMethodDirections.actionFragmentPaymentMethodToFragmentOrderPlaced(
                    args.order.copy(
                        payment = paymentMethod,
                    ),
                    args.uid
                )
            )
        }
    }

    private fun setUpRadioButton() = binding.apply {
        radioButtonCod.setOnClickListener {
            paymentMethod = resources.getString(
                com.farm.ezy.core.R.string.cod,
            )
        }
        radioButtonOnline.setOnClickListener {
            resources.getString(
                com.farm.ezy.core.R.string.online,
            )
        }
    }
}