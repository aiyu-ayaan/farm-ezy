package com.farm.ezy.login

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farm.ezy.core.utils.checkNumber
import com.farm.ezy.login.databinding.FragmentLoginBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
    }


    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding.apply {
            buttonContinue.setOnClickListener {
                handleButtonClick(binding.outlinedTextField.editText?.text.toString())
            }
        }
        navigateToHome()
//        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        if (auth.currentUser != null)
            navigateToHome()
    }

    private fun navigateToHome() {
        val action = LogInFragmentDirections.actionLogInFragmentToNavGraphHome()
        findNavController().navigate(action)
    }

    private fun handleButtonClick(number: String) {
        if (checkNumber(number)) {
            binding.outlinedTextField.error = null
            navigateToOtpFragment(number)
        } else {
            binding.outlinedTextField.error =
                context?.getString(com.farm.ezy.core.R.string.phone_number_warning)
        }
    }

    private fun navigateToOtpFragment(number: String) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        val action = LogInFragmentDirections
            .actionLogInFragmentToOtpFragment(
                context?.getString(
                    com.farm.ezy.core.R.string.phone_number,
                    number
                ) ?: ""
            )
        findNavController().navigate(action)
    }
}