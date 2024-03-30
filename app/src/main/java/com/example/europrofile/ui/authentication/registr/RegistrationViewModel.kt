package com.example.europrofile.ui.authentication.registr

import com.example.europrofile.core.ui.BaseViewModel
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val  authRepository: AuthRepository) : BaseViewModel(){
    override val sendRequest: suspend (String, String) -> RequestResult<User> = {
        email, password -> authRepository.signUp(email, password)
    }


}