package com.farm.ezy.home.item_detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farm.ezy.core.models.items.ItemSet
import com.farm.ezy.core.models.wishlist.WishlistSet
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.home.R
import com.farm.ezy.home.databinding.FragmentItemDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : Fragment(R.layout.fragment_item_detail) {
    private val binding: FragmentItemDetailBinding by viewBinding()
    private val viewModel: ItemDetailViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var db: FirebaseFirestore
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
                buttonWishList.setOnClickListener {
                    addItemToWishList()
                }
            }
        }
    }

    private fun addItemToWishList() = viewModel.item?.let { item ->
        val ref = db.collection("Users").document(auth.uid!!)
            .collection("wishlist")
        val id = ref.document().id
        val wishlistSet = WishlistSet(
            item.name,
            item.name_hi,
            item.img_link1,
            ((if (binding.editTextQuantity.text.toString()
                    .isEmpty()
            ) 1 else binding.editTextQuantity.text.toString().toInt()) * item.price).toString(),
            if (binding.editTextQuantity.text.toString()
                    .isEmpty()
            ) 1 else binding.editTextQuantity.text.toString().toInt(),
            item.type,
            id,
            item.path
        )
        ref.document(id).set(wishlistSet).addOnSuccessListener {
            Toast.makeText(
                requireContext(),
                resources.getString(com.farm.ezy.core.R.string.added),
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigationToAddress(item: ItemSet) {
        val uid = auth.uid
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
                        ) 1 else binding.editTextQuantity.text.toString().toInt()
                    )
                    putString("wishListPath", "")
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