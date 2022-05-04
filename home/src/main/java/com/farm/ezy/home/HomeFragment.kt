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

    private lateinit var insecticidesAdapter: HomeAdapter
    private lateinit var growthPromoterAdapter: HomeAdapter
    private lateinit var herbicidesAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insecticidesAdapter = HomeAdapter {
            navigateToDetail(it)
        }
        growthPromoterAdapter = HomeAdapter {
            navigateToDetail(it)
        }
        herbicidesAdapter = HomeAdapter {
            navigateToDetail(it)
        }
        binding.apply {
            showInsecticides.apply {
                adapter = insecticidesAdapter
                layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            }
            showGrowthPromoters.apply {
                adapter = growthPromoterAdapter
                layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            }
            showHerbicides.apply {
                adapter = herbicidesAdapter
                layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            }
            textViewGrowthPromotersViewAll.setOnClickListener {
                navigateToViewAll(resources.getString(com.farm.ezy.core.R.string.GROWTH_PROMOTERS))
            }
            textViewInsecticidesViewAll.setOnClickListener {
                navigateToViewAll(resources.getString(com.farm.ezy.core.R.string.INSECTICIDES))
            }
            textViewHerbicidesViewAll.setOnClickListener {
                navigateToViewAll(resources.getString(com.farm.ezy.core.R.string.HERBICIDES))
            }
        }
        loadData()
    }

    private fun navigateToViewAll(type: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFragmentShowAll(
                type
            )
        )
    }

    private fun navigateToDetail(itemGet: ItemGet) {
        val action = HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(
            itemMapper.mapFromEntity(itemGet)
        )
        findNavController().navigate(action)
    }

    private fun loadData() = lifecycleScope.launchWhenStarted {
        viewModel.dataStateInsecticides.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    insecticidesAdapter.submitList(dataState.data)
                }
            }
        }
        viewModel.dataStateHerbicides.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    herbicidesAdapter.submitList(dataState.data)
                }
            }
        }
        viewModel.dataStateGrowthPromoter.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    growthPromoterAdapter.submitList(dataState.data)
                }
            }
        }
    }
}