package com.example.europrofile.domain

interface AuthRepository {

    suspend fun signInWithEmailPassword(email: String, password: String): com.example.europrofile.data.AuthResult

    suspend fun signUpWithEmailPassword(email: String, password: String): com.example.europrofile.data.AuthResult

}