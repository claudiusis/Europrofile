package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import com.example.europrofile.ui.tabs.main.condition.CondTypeCard
import kotlinx.coroutines.flow.Flow

interface ConditionInfo {
    suspend fun getConditionsList() : Flow<RequestResult<CondTypeCard>>
    suspend fun getPage(url : String, imgList: List<String>) : RequestResult<WebPageData>
}