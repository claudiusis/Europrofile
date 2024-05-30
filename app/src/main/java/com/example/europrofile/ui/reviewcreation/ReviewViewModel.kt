package com.example.europrofile.ui.reviewcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europrofile.core.Variants
import com.example.europrofile.data.RequestResult
import com.example.europrofile.domain.Review
import com.example.europrofile.domain.ReviewRepository
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val repository: ReviewRepository): ViewModel() {

    private val _reviewGetState = MutableLiveData<List<ViewReview>>()
    val reviewGetState: LiveData<List<ViewReview>> = _reviewGetState

    private val _statusOfLoad = MutableLiveData<RequestResult<ViewReview>>()

    private val _reviewSetState = MutableLiveData<RequestResult<Review>>()
    val reviewSetState: LiveData<RequestResult<Review>> = _reviewSetState

    private val _userReviews = MutableLiveData<RequestResult<List<ViewReview>>>()
    val userReviews : LiveData<RequestResult<List<ViewReview>>> = _userReviews

    init {
        viewModelScope.launch(Dispatchers.IO){
           repository.subscribePostChanges().collect{
               when(it){
                   is RequestResult.Success -> {

                       _reviewGetState.postValue(it.data)

                   }
                   else -> {}
               }
           }
        }
    }

    fun addReview(review: Review) =
        viewModelScope.launch {
            _reviewSetState.postValue(RequestResult.Loading)
            val result = repository.loadReview(review)
            _reviewSetState.postValue(result)
        }

    fun updateReview(reviewId: String, type: Variants, userId: String) {

        val review = _reviewGetState.value?.find { it.id==reviewId }

        when(type) {
            Variants.Like -> {
                if (review!!.listOfUserLikes.contains(userId)) {
                    review.listOfUserLikes.remove(userId)
                } else {
                    review.listOfUserLikes.add(userId)
                }

                if (review.listOfUserDisLikes.contains(userId)) {
                    review.listOfUserDisLikes.remove(userId)
                }
            }

            Variants.Dislike -> {

                if (review!!.listOfUserDisLikes.contains(userId)) {
                    review.listOfUserDisLikes.remove(userId)
                } else {
                    review.listOfUserDisLikes.add(userId)
                }

                if (review.listOfUserLikes.contains(userId)) {
                    review.listOfUserLikes.remove(userId)
                }

            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLike(review?:ViewReview())
        }
    }

    fun getReviews() =
        viewModelScope.launch {
            _statusOfLoad.postValue(RequestResult.Loading)
            repository.getReviewList().collect { result ->
                when(result){
                    is RequestResult.Success -> {

                        val data = (_reviewGetState.value?: emptyList()) + result.data

                        _reviewGetState.postValue(data.distinctBy { it.id })
                    }
                    is RequestResult.Loading,
                    is RequestResult.Error -> {

                    }
                }
            }
            _statusOfLoad.postValue(RequestResult.Success(_reviewGetState.value?.last()?: ViewReview()))
        }



/*    fun getUserReviews(uid: String){
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
    }*/
}