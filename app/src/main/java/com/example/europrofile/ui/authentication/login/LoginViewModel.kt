package com.example.europrofile.ui.authentication.login

import com.example.europrofile.core.ui.BaseViewModel
import com.example.europrofile.data.AuthResult
import com.example.europrofile.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository)  : BaseViewModel() {

    override val sendRequest: suspend (String, String) -> AuthResult = {
        email, password -> authRepository.signInWithEmailPassword(email, password)
    }
}