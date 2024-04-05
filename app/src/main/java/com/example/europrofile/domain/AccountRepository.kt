package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult

interface AccountRepository {
    fun getUserInfo(uid: String, result: (RequestResult<User>)->Unit)
}