package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult

interface AccountRepository {
    fun getUserInfo(result: (RequestResult<User>)->Unit)
}