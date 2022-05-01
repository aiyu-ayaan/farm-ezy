package com.farm.ezy.profile.address

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farm.ezy.core.models.address.AddressMapper
import com.farm.ezy.core.models.address.AddressSet
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.NoItemFoundException
import com.farm.ezy.profile.NavGraphProfileDirections
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentAddressBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAddress : Fragment(R.layout.fragment_address) {

    private val binding: FragmentAddressBinding by viewBinding()
    private val viewModel: AddressViewModel by viewModels()
    private lateinit var addressAdapter: AddressAdapter

    @Inject
    lateinit var mapper: AddressMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addressAdapter = AddressAdapter(edit = {
            navigateToAddEditBottomSheet(mapper.mapFromEntity(it))
        }, delete = {
            navigateToDelete(mapper.mapFromEntity(it))
        })
        binding.apply {
            recyclerViewAddress.apply {
                adapter = addressAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            buttonAddAddress.setOnClickListener {
                navigateToAddEditBottomSheet()
            }
        }

        getDate()
    }

    private fun navigateToDelete(address: AddressSet) {
        val action = NavGraphProfileDirections.actionGlobalDialogAddress(viewModel.uid!!, address)
        findNavController().navigate(action)
    }

    private fun navigateToAddEditBottomSheet(addressSet: AddressSet? = null) {
        val action =
            if (addressSet != null) FragmentAddressDirections.actionFragmentAddressToBottomSheetAddEditAddress(
                address = addressSet, uid = viewModel.uid!!
            )
            else FragmentAddressDirections.actionFragmentAddressToBottomSheetAddEditAddress(uid = viewModel.uid!!)
        findNavController().navigate(action)
    }

    private fun getDate() = lifecycleScope.launchWhenStarted {
        viewModel.getAddress(viewModel.uid!!).collect { dataState ->
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
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    addressAdapter.submitList(dataState.data)
                }
            }
        }
    }
}