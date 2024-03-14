package com.example.europrofile.data

import com.example.europrofile.domain.User

sealed class AuthResult {

    class Success(val user: User): AuthResult()

    class Error(val e: Exception): AuthResult()

    object Loading : AuthResult()

}