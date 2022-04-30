package com.farm.ezy.login.otp_fragment

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farm.ezy.core.models.user.User
import com.farm.ezy.core.utils.snackBar
import com.farm.ezy.login.R
import com.farm.ezy.login.databinding.FragmentOtpBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class OtpFragment : Fragment(R.layout.fragment_otp) {

    private val binding: FragmentOtpBinding by viewBinding()
    private val viewModel: OtpViewModel by viewModels()

    @Inject
    lateinit var db: FirebaseFirestore

    @Inject
    lateinit var auth: FirebaseAuth

    private var storedVerificationId: String? = null
    private var countDown: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textViewOtpSendDetail.hint =
                context?.getString(com.farm.ezy.core.R.string.otp_send_detail, viewModel.number)

            otpView.otpListener = object : OTPListener {
                override fun onInteractionListener() {

                }

                override fun onOTPComplete(otp: String) {
                    verifyCode(otp)
                }

            }
        }
        viewModel.number?.let {
//            lifecycleScope.launchWhenStarted {
//                delay(2000)
//                navigateToHome("ererejjerhejrhj")
//            }
            sendOTP(it)
        }

    }

    private fun sendOTP(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationFailed(p0: FirebaseException) {
            binding.root.snackBar(p0.message.toString(), null, null)
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            storedVerificationId = p0
            binding.progressBarOtp.isVisible = false
            binding.root.snackBar(
                context?.getString(com.farm.ezy.core.R.string.code_send)!!,
                null,
                null
            )
            setComeDown()
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            val s = p0.smsCode
            if (s != null) {// null safety
                binding.otpView.setOTP(s)
                verifyCode(s)
            }
        }
    }

    private fun setComeDown() {
        countDown = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimer(millisUntilFinished)
            }

            override fun onFinish() {
                resend()
            }
        }.start()
    }

    private fun resend() =
        binding.apply {
            textViewResend.apply {
                alpha = 1f
                isClickable = true
                setOnClickListener {
                    setComeDown()
                    sendOTP(viewModel.number!!)
                    alpha = 0.4f
                    binding.root.snackBar(
                        context?.getString(com.farm.ezy.core.R.string.code_send)!!,
                        null,
                        null
                    )
                }
            }
        }

    private fun updateTimer(millisUntilFinished: Long) = binding.apply {
        textViewTime.isVisible = true
        textViewResend.isVisible = true
        val min = (millisUntilFinished / 1000) / 60
        val sec = (millisUntilFinished / 1000) % 60
        textViewTime.text = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
    }

    private fun verifyCode(code: String) {
        storedVerificationId?.let {
            val phoneAuthCredential = PhoneAuthProvider.getCredential(it, code)
            Log.d("Auth", "verifyCode: $storedVerificationId")
            signInWithPhoneAuthCredential(credential = phoneAuthCredential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    countDown?.cancel()
                    it.result?.user?.uid?.let { uid ->
                        if (it.result?.additionalUserInfo?.isNewUser == true)
                            signUp(uid)
                        else
                            navigateToHome()
                    }
                } else
                    it.exception?.printStackTrace()
            }
    }


    private fun signUp(uid: String) {
        val ref = db.collection("Users")
        val user = User(viewModel.number!!, uid)
        ref.document(uid).set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    navigateToHome()
                } else {
                    auth.signOut()
                    binding.root.snackBar(it.exception.toString(), null, null)
                }
            }
    }

    private fun navigateToHome() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        reenterTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
//        OtpFragmentDirections.actionOtpViewToNavGraphHome().let {
//            findNavController().navigate(
//                it.actionId,
//                Bundle().apply {
//                    putString("uid", uid)
//                }
//            )
//        }
        val action = OtpFragmentDirections.actionOtpViewToNavGraphHome()
        findNavController().navigate(action)

    }


    override fun onDestroy() {
        super.onDestroy()
        countDown?.cancel()
    }
}