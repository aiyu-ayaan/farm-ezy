package com.farm.ezy.profile

import androidx.lifecycle.ViewModel
import com.farm.ezy.core.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUser(
        uid: String
    ) = userRepository.getLoginUser(uid)
}