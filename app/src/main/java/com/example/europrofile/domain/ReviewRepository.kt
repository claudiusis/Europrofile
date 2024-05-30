package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getReviewList(): Flow<RequestResult<ViewReview>>
    suspend fun loadReview(review: Review): RequestResult<Review>
    suspend fun subscribePostChanges() : Flow<RequestResult<List<ViewReview>>>
    suspend fun updateLike(data: ViewReview) : RequestResult<String>

}