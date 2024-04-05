package com.example.europrofile.ui.authentication.login

import com.example.europrofile.core.ui.BaseViewModel
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository)  : BaseViewModel() {
    override suspend fun sendRequest(vararg args: String): RequestResult<User> = authRepository.signInWithEmailPassword(args[0], args[1])
}