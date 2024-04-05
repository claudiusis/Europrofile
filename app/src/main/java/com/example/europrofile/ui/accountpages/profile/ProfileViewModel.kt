package com.example.europrofile.ui.accountpages.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: AccountRepository): ViewModel() {

    private val _userInfo : MutableLiveData<RequestResult<User>> = MutableLiveData()

    val userInfo : LiveData<RequestResult<User>> = _userInfo

    fun getUserInfo(uid: String) {
        _userInfo.postValue(RequestResult.Loading)
        repository.getUserInfo(uid) {
            _userInfo.postValue(it)
        }
    }
}