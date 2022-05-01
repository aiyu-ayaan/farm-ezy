package com.farm.ezy.profile.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farm.ezy.core.models.address.AddressGet
import com.farm.ezy.core.models.address.DiffUtilsAddressGet
import com.farm.ezy.profile.databinding.RowAddressBinding

class AddressAdapter(
    private val edit: (AddressGet) -> Unit,
    private val delete: (AddressGet) -> Unit
) :
    ListAdapter<AddressGet, AddressAdapter.AddressViewHolder>(DiffUtilsAddressGet()) {

    inner class AddressViewHolder(
        private val binding: RowAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonEdit.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    edit.invoke(getItem(pos))
            }
            binding.buttonRemove.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    delete.invoke(getItem(pos))
            }
        }

        fun bind(address: AddressGet) = binding.apply {
            textViewName.text = address.name
            textViewPhoneNumber.text = binding.root.context.resources.getString(
                com.farm.ezy.core.R.string.phone_number_address,
                address.number
            )
            textViewHouse.text = address.houseNo
            textViewAddress.text =
                binding.root.context.resources.getString(
                    com.farm.ezy.core.R.string.address_detail,
                    address.landMark, address.town, address.state, address.posterCode
                )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(
            RowAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}