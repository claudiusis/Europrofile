package com.example.europrofile.ui.detailspage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.ConditionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository : ConditionInfo): ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>>
        get() = _imageUrls

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _info = MutableLiveData<LinkedHashMap<String, String>>()
    val info: LiveData<LinkedHashMap<String, String>>
        get() = _info

    private val _price = MutableLiveData<String>()
    val price: LiveData<String>
        get() = _price

    fun getData(link : String, imgList: List<String>){
        viewModelScope.launch {
            when (val data = repository.getPage(link, imgList)){
                is RequestResult.Success -> {
                    _title.value = data.data.title
                    _price.value = data.data.price
                    _imageUrls.value = data.data.imageUrls
                    _description.value = data.data.description
                    _info.value = data.data.details
                }
                is RequestResult.Error -> {
                    throw Exception("Ошибка получения данных")
                }
                else -> {}
            }
        }
    }

}