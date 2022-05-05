/*
 * BIT Lalpur App
 *
 * Created by Ayaan on 9/29/21, 12:32 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/29/21, 12:32 AM
 */

package com.farm.ezy.profile.component_use

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farm.ezy.core.models.Component
import com.farm.ezy.profile.databinding.RowComponentBinding

class ComponentUseAdapter(
    private val listener: (String) -> Unit
) :
    RecyclerView.Adapter<ComponentUseAdapter.ComponentUseViewHolder>() {

    private var listComponents = listOf<Component>()

    inner class ComponentUseViewHolder constructor(
        private val binding: RowComponentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.invoke(listComponents[position].link)
                }
            }
        }

        fun bind(com: Component) = binding.apply {
            textViewName.text = com.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentUseViewHolder =
        ComponentUseViewHolder(
            RowComponentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ComponentUseViewHolder, position: Int) {
        holder.bind(listComponents[position])
    }

    override fun getItemCount(): Int =
        listComponents.size

    fun submitList(list: List<Component>) {
        this.listComponents = list
    }


}