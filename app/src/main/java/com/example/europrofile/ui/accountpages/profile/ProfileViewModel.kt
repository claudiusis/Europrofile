package com.example.europrofile.ui.accountpages.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: AccountRepository, private val authRepository: AuthRepository): ViewModel() {

    private val _userInfo : MutableLiveData<RequestResult<User>> = MutableLiveData()

    val userInfo : LiveData<RequestResult<User>> = _userInfo

    fun getUserInfo(uid: String) {
        viewModelScope.launch {
            _userInfo.postValue(RequestResult.Loading)
            repository.subscribeToUser(uid).collect{
                _userInfo.postValue(it)
            }
        }
    }

    fun changeInfo(user : User, type: String){
        _userInfo.postValue(RequestResult.Success(user))
        viewModelScope.launch {
            repository.changeInfo(user, type)
        }
    }

    fun logOut() {
        authRepository.logOut()
    }
}