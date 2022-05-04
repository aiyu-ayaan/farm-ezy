package com.farm.ezy.profile

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farm.ezy.core.models.user.UserGet
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.loadImageCircular
import com.farm.ezy.profile.databinding.FragmentProfileBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FragmentProfile : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var db: FirebaseFirestore
    private val viewModel: ProfileViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonOrders.setOnClickListener {
                navigateToOrder()
            }
            buttonAddress.setOnClickListener {
                navigateToAddress()
            }
        }
        getData()
    }

    private fun navigateToAddress() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        FragmentProfileDirections.actionFragmentProfileToFragmentAddress().let {
            findNavController().navigate(it.actionId,
                Bundle().apply {
                    putString("uid", auth.currentUser?.uid ?: "zcUL7f9HDGbJWdxZ1bYLcvfwTFA3")
                })
        }
    }

    private fun navigateToOrder() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        val action =
            FragmentProfileDirections.actionFragmentProfileToOrderFragment(
                auth.currentUser?.uid ?: "zcUL7f9HDGbJWdxZ1bYLcvfwTFA3"
            )
        findNavController().navigate(action)

    }

    private fun getData() {
        lifecycleScope.launchWhenStarted {
            viewModel.getUser(auth.uid ?: "zcUL7f9HDGbJWdxZ1bYLcvfwTFA3").collectLatest {
                when (it) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "${it.exception}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        setViews(it.data)
                    }
                }
            }
        }
    }

    private fun setViews(data: UserGet) {
        binding.apply {
            textViewUserName.text = data.name
            textViewPhoneNumber.text = data.number
            data.profileImage?.loadImageCircular(
                binding.root,
                binding.imageView,
                binding.progressCircularProfile
            )
        }
    }
}