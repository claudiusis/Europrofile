package com.example.europrofile.ui.tabs.main.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.domain.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConditionerViewModel @AssistedInject constructor(private val repository: ConditionInfo, @Assisted private val user: User): ViewModel() {

    private val _conditionCard : MutableLiveData<List<CondTypeCard>> = MutableLiveData()
    val condCard : LiveData<List<CondTypeCard>> = _conditionCard

    private val _favourites : MutableLiveData<List<Conditioner>> = MutableLiveData()
    val favourites = _favourites

    private val _status : MutableLiveData<RequestResult<CondTypeCard>> = MutableLiveData()
    val status : LiveData<RequestResult<CondTypeCard>> = _status

    @AssistedFactory
    interface Factory {
        fun create(user: User) : ConditionerViewModel
    }

    init {
        getConditionType(user)
    }

    private fun getConditionType(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(RequestResult.Loading)
            repository.getConditionsList().collect {
                result ->
                when(result){
                    is RequestResult.Success -> {

                        _conditionCard.postValue((_conditionCard.value ?: emptyList()) + result.data)

/*                        result.data.condList.forEach {
                            if (user.listOfFavourites.contains(it.title)){
                                _favourites.postValue(
                                    (_favourites.value?: emptyList()) + it
                                )
                            }
                        }*/


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

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            user : User
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(user) as T
            }
        }
    }

}