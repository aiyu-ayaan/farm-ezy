/*
 * BIT Lalpur App
 *
 * Created by Ayaan on 4/25/22, 11:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 4/25/22, 11:25 PM
 */

package com.farm.ezy.profile.component_use

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.farm.ezy.core.repositories.ComponentList
import com.farm.ezy.core.utils.openLinks
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentComponentUseBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentComponentUse : Fragment(R.layout.fragment_component_use) {
    private val binding: FragmentComponentUseBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val componentUseAdapter = ComponentUseAdapter {
            handleClick(it)
        }
        binding.apply {
            showContent.apply {
                adapter = componentUseAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        componentUseAdapter.submitList(ComponentList.list)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun handleClick(it: String) {
        it.openLinks(requireActivity())
    }

}