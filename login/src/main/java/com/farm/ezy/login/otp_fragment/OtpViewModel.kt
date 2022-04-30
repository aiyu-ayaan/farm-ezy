package com.farm.ezy.login.otp_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {
    val number = state.get<String>("number")
}