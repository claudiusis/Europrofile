package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import com.example.europrofile.ui.tabs.main.condition.CondTypeCard

interface ConditionInfo {
    suspend fun getConditionsList() : RequestResult<List<CondTypeCard>>
}