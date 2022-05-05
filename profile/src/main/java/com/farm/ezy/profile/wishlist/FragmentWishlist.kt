package com.farm.ezy.profile.wishlist

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farm.ezy.core.models.items.ItemMapper
import com.farm.ezy.core.models.wishlist.WishlistGet
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.NoItemFoundException
import com.farm.ezy.profile.R
import com.farm.ezy.profile.databinding.FragmentWishListBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentWishlist : Fragment(R.layout.fragment_wish_list) {

    @Inject
    lateinit var db: FirebaseFirestore

    @Inject
    lateinit var itemMapper: ItemMapper
    private val binding: FragmentWishListBinding by viewBinding()

    private val viewModel: WishListViewModel by viewModels()
    private lateinit var wishListAdapter: WishListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishListAdapter = WishListAdapter(
            buy = {
                navigateToBuy(it)
            }, delete = { deleteItem(it) }
        )
        binding.apply {
            showWishList.apply {
                adapter = wishListAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        loadData()
    }

    private fun navigateToBuy(wish: WishlistGet) = lifecycleScope.launchWhenStarted {
        viewModel.getOrderDetail(wish.type!!, wish.itemPath!!).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    FragmentWishlistDirections.actionFragmentWishlistToNavGraphAddress().let {
                        findNavController().navigate(it.actionId,
                            Bundle().apply
                            {
                                putString("uid", viewModel.uid!!)
                                putParcelable("item", itemMapper.mapFromEntity(dataState.data))
                                putInt(
                                    "quantity",
                                    wish.quantity!!
                                )
                            })
                    }
                }
            }
        }
    }

    private fun navigateToDetail(it: WishlistGet) = lifecycleScope.launchWhenStarted {
        viewModel.getOrderDetail(it.type!!, it.itemPath!!).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), "${dataState.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    findNavController().navigate(
                        FragmentWishlistDirections.actionFragmentWishlistToItemDetailFragment(
                            itemMapper.mapFromEntity(dataState.data)
                        )
                    )
                }
            }
        }
    }

    private fun deleteItem(it: WishlistGet) {
        db.collection("Users").document(viewModel.uid!!)
            .collection("wishlist").document(it.path!!)
            .delete().addOnCompleteListener {
                Toast.makeText(
                    requireContext(),
                    resources.getString(com.farm.ezy.core.R.string.deleted),
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadData() = lifecycleScope.launchWhenStarted {
        viewModel.getWishLists(viewModel.uid!!).collect { dataState ->
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
                    wishListAdapter.submitList(dataState.data)
                }
            }
        }
    }
}