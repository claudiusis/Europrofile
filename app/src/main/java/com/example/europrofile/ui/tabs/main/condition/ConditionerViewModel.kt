package com.example.europrofile.ui.tabs.main.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConditionerViewModel @Inject constructor(private val repository: ConditionInfo): ViewModel() {

    private val _conditionCard : MutableLiveData<List<CondTypeCard>> = MutableLiveData()
    val condCard : LiveData<List<CondTypeCard>> = _conditionCard

    private val _favourites : MutableLiveData<List<Conditioner>> = MutableLiveData()
    val favourites = _favourites

    private val _status : MutableLiveData<RequestResult<CondTypeCard>> = MutableLiveData()
    val status : LiveData<RequestResult<CondTypeCard>> = _status

    init {
        getConditionType()
    }

    private fun getConditionType(){
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(RequestResult.Loading)
            repository.getConditionsList().collect {
                result ->
                when(result){
                    is RequestResult.Success -> {

                        _conditionCard.postValue((_conditionCard.value ?: emptyList()) + result.data)

                    }
                    is RequestResult.Error -> {}
                    is RequestResult.Loading -> {}
                }
            }
            _status.postValue(RequestResult.Success(_conditionCard.value?.last()?: CondTypeCard()))
        }
    }

    fun refactorFavourites(user : User) {
        _conditionCard.value?.forEach { condTypeCard ->
            condTypeCard.condList.forEach { conditioner ->
                if (user.listOfFavourites.contains(conditioner.title)){
                    _favourites.value = ((_favourites.value?: emptyList()) + conditioner).distinctBy { it.title }
                } else {
                    _favourites.value = (_favourites.value?: emptyList()) - conditioner
                }
            }
        }
    }

}