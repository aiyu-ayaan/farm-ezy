package com.farm.ezy.home.item_detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farm.ezy.core.models.items.ItemSet
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.home.R
import com.farm.ezy.home.databinding.FragmentItemDetailBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : Fragment(R.layout.fragment_item_detail) {
    private val binding: FragmentItemDetailBinding by viewBinding()
    private val viewModel: ItemDetailViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.item?.let { item ->
                setViews(item)
                buttonBuy.setOnClickListener {
                    navigationToAddress(item)
                }
            }
        }

    }

    private fun navigationToAddress(item: ItemSet) {
        val uid = auth.uid ?: "zcUL7f9HDGbJWdxZ1bYLcvfwTFA3"
        ItemDetailFragmentDirections.actionItemDetailFragmentToNavGraphAddress().let {
            findNavController().navigate(
                it.actionId,
                Bundle().apply {
                    putString("uid", uid)
                    putParcelable("item", item)
                    putInt(
                        "quantity",
                        if (binding.editTextQuantity.text.toString()
                                .isEmpty()
                        ) 0 else binding.editTextQuantity.text.toString().toInt()
                    )
                }
            )
        }
    }

    private fun setViews(item: ItemSet) = binding.apply {
        textViewItemName.text = item.name
        textViewDescription.text = item.des
        textViewItemPrice.text =
            resources.getString(com.farm.ezy.core.R.string.price, item.price.toString())
        textViewItemSize.text = item.size
        item.img_link1.loadImageDefault(
            binding.root,
            binding.imageViewItem,
            binding.progressBarItem
        )
        textViewSeller.text = getString(com.farm.ezy.core.R.string.seller, item.seller)
    }
}