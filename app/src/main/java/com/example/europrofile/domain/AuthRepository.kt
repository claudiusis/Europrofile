package com.example.europrofile.domain

import com.example.europrofile.data.RequestResult

interface AuthRepository {

    suspend fun signInWithEmailPassword(email: String, password: String): RequestResult<User>

    suspend fun signUp(name: String, phoneNumber: String, email: String, password: String) : RequestResult<User>

}