package com.farm.ezy.profile.order_fragment

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.NoItemFoundException
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentOrdersBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment(R.layout.fragment_orders) {

    private val binding: FragmentOrdersBinding by viewBinding()
    private val viewModel: OrderViewModel by viewModels()


    @Inject
    lateinit var db: FirebaseFirestore
    private lateinit var orderAdapter: OrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderAdapter = OrderAdapter {

        }
        binding.apply {
            recyclerViewShowOrders.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = orderAdapter
            }
        }
        getData()
    }

    private fun getData() = lifecycleScope.launchWhenCreated {
        viewModel.getOrders(viewModel.uid!!).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    if (dataState.exception is NoItemFoundException) {
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "${dataState.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                DataState.Loading -> {}
                is DataState.Success -> {
                    orderAdapter.submitList(dataState.data)
                }
            }
        }
    }
}