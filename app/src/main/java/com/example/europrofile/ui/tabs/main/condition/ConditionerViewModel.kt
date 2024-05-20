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

    private val _conditionCard : MutableLiveData<List<CondTypeCard>> = MutableLiveData()
    val condCard : LiveData<List<CondTypeCard>> = _conditionCard

    private val _status : MutableLiveData<RequestResult<CondTypeCard>> = MutableLiveData()
    val status : LiveData<RequestResult<CondTypeCard>> = _status

    fun getConditionType(){
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(RequestResult.Loading)
            repository.getConditionsList().collect {
                result ->
                when(result){
                    is RequestResult.Success -> {
                        _conditionCard.postValue((_conditionCard.value ?: emptyList()) + result.data)
                    }
                    is RequestResult.Error -> {

                    }
                    is RequestResult.Loading -> {

                    }
                }
            }
            _status.postValue(RequestResult.Success(_conditionCard.value?.last()?: CondTypeCard()))
        }
    }


}