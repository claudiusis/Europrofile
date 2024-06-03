package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getUserInfo(uid: String, result: (RequestResult<User>)->Unit)
    suspend fun changeInfo(user: User, type: String): RequestResult<User>
    suspend fun subscribeToUser(uid : String) : Flow<RequestResult<User>>

}