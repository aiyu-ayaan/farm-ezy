package com.farm.ezy.home.show_all

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
import com.farm.ezy.core.utils.NoItemFoundException
import com.farm.ezy.home.R
import com.farm.ezy.home.databinding.FragmentShowAllBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentShowAll : Fragment(R.layout.fragment_show_all) {

    private val binding: FragmentShowAllBinding by viewBinding()
    private val viewModel: ShowAllViewModel by viewModels()

    private lateinit var showAllAdapter: ShowAllAdapter

    @Inject
    lateinit var itemMapper: ItemMapper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAllAdapter = ShowAllAdapter {
            navigateToDetail(it)
        }
        binding.apply {
            recyclerViewShowAll.apply {
                adapter = showAllAdapter
                layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            }
        }
        viewModel.getData(viewModel.type!!)
        showData()
    }

    private fun showData() = lifecycleScope.launchWhenStarted {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    if (dataState.exception is NoItemFoundException)
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(
                            requireContext(),
                            "${dataState.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                DataState.Loading -> {}
                is DataState.Success -> {
                    showAllAdapter.submitList(dataState.data)
                }
            }
        }
    }

    private fun navigateToDetail(itemGet: ItemGet) {
        val action = FragmentShowAllDirections.actionFragmentShowAllToItemDetailFragment(
            itemMapper.mapFromEntity(itemGet)
        )
        findNavController().navigate(action)
    }
}