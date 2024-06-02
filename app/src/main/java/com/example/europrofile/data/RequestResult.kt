package com.example.europrofile.data

sealed class RequestResult<out T> {
    class Success<out T>(val data: T): RequestResult<T>()
    class Error(val e: Exception): RequestResult<Nothing>()
    object Loading : RequestResult<Nothing>()

}