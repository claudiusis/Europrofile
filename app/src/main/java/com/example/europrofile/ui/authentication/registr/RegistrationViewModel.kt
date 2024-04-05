package com.example.europrofile.ui.authentication.registr

import com.example.europrofile.core.ui.BaseViewModel
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val  authRepository: AuthRepository) : BaseViewModel(){
    override suspend fun sendRequest(vararg args: String): RequestResult<User> =
        authRepository.signUp(args[0], args[1], args[2], args[3])

}