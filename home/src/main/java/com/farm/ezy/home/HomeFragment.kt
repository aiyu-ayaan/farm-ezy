package com.farm.ezy.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.farm.ezy.core.models.items.ItemGet
import com.farm.ezy.core.models.items.ItemMapper
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var itemMapper: ItemMapper

    private lateinit var homeAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter {
            navigateToDetail(it)
        }
        binding.apply {
            showInsecticides.apply {
                adapter = homeAdapter
                layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            }
        }
        loadData()
    }

    private fun navigateToDetail(itemGet: ItemGet) {
        val action = HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(
            itemMapper.mapFromEntity(itemGet)
        )
        findNavController().navigate(action)
    }

    private fun loadData() = lifecycleScope.launchWhenStarted {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    homeAdapter.submitList(dataState.data)
                }
            }
        }
    }
}