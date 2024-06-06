package com.example.europrofile.ui.tabs.addorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.Order
import com.example.europrofile.domain.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {

    private val _statusOfLoad : MutableLiveData<RequestResult<String>> = MutableLiveData()
    val statusOfLoad : LiveData<RequestResult<String>> = _statusOfLoad

    private val _orders : MutableLiveData<List<Order>> = MutableLiveData()
    val orders : LiveData<List<Order>> = _orders


    fun getOrders(uid : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _statusOfLoad.postValue(RequestResult.Loading)
            repository.getOrders(uid).collect {
                when (it) {
                    is RequestResult.Success -> {
                        _orders.postValue(((_orders.value?: emptyList())+it.data).distinctBy { it.id })
                    }
                    is RequestResult.Error -> {

                    }
                    is RequestResult.Loading -> {}
                }
            }
            _statusOfLoad.postValue(RequestResult.Success("OK"))
        }
    }

    fun makeOrder(order : Order) {
        viewModelScope.launch(Dispatchers.IO) {
            _statusOfLoad.postValue(RequestResult.Loading)
            val result = repository.uploadOrder(order)
            _statusOfLoad.postValue(result)
        }
    }

}