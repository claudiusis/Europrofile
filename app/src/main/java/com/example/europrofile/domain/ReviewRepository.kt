package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview

interface ReviewRepository {

    suspend fun getReviewList(): RequestResult<List<ViewReview>>

    suspend fun loadReview(review: Review): RequestResult<Review>

}