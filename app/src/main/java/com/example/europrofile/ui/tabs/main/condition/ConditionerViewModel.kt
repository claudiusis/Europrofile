package com.example.europrofile.ui.tabs.main.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.ConditionInfoImpl
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConditionerViewModel @Inject constructor(private val repository: ConditionInfoImpl): ViewModel() {

    private val _conditionCard : MutableLiveData<List<CondTypeCard>> = MutableLiveData()
    val condCard : LiveData<List<CondTypeCard>> = _conditionCard

    private val _favourites : MutableLiveData<List<Conditioner>> = MutableLiveData()
    val favourites = _favourites

    private val _status : MutableLiveData<RequestResult<CondTypeCard>> = MutableLiveData()
    val status : LiveData<RequestResult<CondTypeCard>> = _status

/*    init {
        getConditionType(user)
    }*/

    fun getConditionType(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(RequestResult.Loading)
            repository.getConditionsList().collect {
                result ->
                when(result){
                    is RequestResult.Success -> {

                        _conditionCard.postValue((_conditionCard.value ?: emptyList()) + result.data)

                        result.data.condList.forEach {
                            if (user.listOfFavourites.contains(it.title)){
                                _favourites.postValue(
                                    (_favourites.value?: emptyList()) + it
                                )
                            }
                        }


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

    fun addFavourites(elem: Conditioner){
        _favourites.value = (_favourites.value?: emptyList()) + elem
    }

    fun removeFavourites(elem: Conditioner){
        _favourites.value = _favourites.value?.toMutableList()?.apply { remove(elem) }?.toList()
    }

}