package com.example.europrofile.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    abstract suspend fun sendRequest(vararg args: String) : RequestResult<User>

    private val _authState = MutableLiveData<RequestResult<User>>()
    val authState : LiveData<RequestResult<User>> get() = _authState
    fun sendCredentials(vararg args: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _authState.postValue(RequestResult.Loading)
            val result = sendRequest(*args)
            _authState.postValue(result)


        }

    }

}