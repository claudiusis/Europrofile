package com.example.europrofile.ui.reviewcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.Review
import com.example.europrofile.domain.ReviewRepository
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ReviewViewModel @Inject constructor(private val repository: ReviewRepository): ViewModel() {

    private val _reviewGetState = MutableLiveData<RequestResult<List<ViewReview>>>()
    val reviewGetState: LiveData<RequestResult<List<ViewReview>>> = _reviewGetState

    private val _reviewSetState = MutableLiveData<RequestResult<Review>>()
    val reviewSetState: LiveData<RequestResult<Review>> = _reviewSetState

    private val _userReviews = MutableLiveData<RequestResult<List<ViewReview>>>()
    val userReviews : LiveData<RequestResult<List<ViewReview>>> = _userReviews

    fun addReview(review: Review) =
        viewModelScope.launch {
            _reviewSetState.postValue(RequestResult.Loading)
            val result = repository.loadReview(review)
            _reviewSetState.postValue(result)
        }

    fun getReviews() =
        viewModelScope.launch {
            _reviewGetState.postValue(RequestResult.Loading)
            val res = repository.getReviewList()
            _reviewGetState.postValue(res)
        }

    fun getUserReviews(uid: String){
        if ((reviewGetState.value as RequestResult.Success).data.isEmpty()){
            viewModelScope.launch {

            }
        } else {
            _userReviews.postValue(RequestResult.Success(
                (_reviewGetState.value as RequestResult.Success).data.filter {
                    it.idOfUser == uid
                })
            )
        }
    }
}