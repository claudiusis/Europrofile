package com.example.europrofile.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    abstract val sendRequest : suspend (String, String) -> AuthResult

    private val _authState = MutableLiveData<AuthResult>()

    val authState : LiveData<AuthResult> get() = _authState

    fun sendCredentials(email: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _authState.postValue(AuthResult.Loading)
            sendRequest(email, password)
            val result = sendRequest.invoke(email, password)
            _authState.postValue(result)
        }

    }
}