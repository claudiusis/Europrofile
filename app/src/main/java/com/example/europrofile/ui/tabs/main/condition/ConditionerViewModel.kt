package com.example.europrofile.ui.tabs.main.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.ConditionInfoImpl
import com.example.europrofile.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConditionerViewModel @Inject constructor(private val repository: ConditionInfoImpl): ViewModel() {

    private val _conditionCard : MutableLiveData<RequestResult<List<CondTypeCard>>> = MutableLiveData()
    val condCard : LiveData<RequestResult<List<CondTypeCard>>> = _conditionCard

    fun getConditionType(){
        viewModelScope.launch(Dispatchers.IO) {
            _conditionCard.postValue(RequestResult.Loading)
            val result = repository.getConditionsList()
            _conditionCard.postValue(result)
        }
    }

}