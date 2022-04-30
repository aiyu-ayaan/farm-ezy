package com.farm.ezy.profile

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.farm.ezy.core.models.user.UserGet
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.loadImageCircular
import com.farm.ezy.profile.databinding.FragmentProfileBinding
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
//        getUser(auth.uid!!)
        lifecycleScope.launchWhenStarted {
            viewModel.getUser(auth.uid!!).collectLatest {
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

    private fun getUser(uid: String) {
        db.collection("Users").document(uid)
            .addSnapshotListener { documentSnapShot, _ ->
                val user = documentSnapShot?.toObject(UserGet::class.java)
                setViews(user!!)
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