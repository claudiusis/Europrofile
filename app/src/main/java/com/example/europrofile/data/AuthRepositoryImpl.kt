package com.example.europrofile.data

import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth)  : AuthRepository {

    override suspend fun signInWithEmailPassword(email: String, password: String): AuthResult {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User(user.email ?: " ", user.uid))

        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): AuthResult {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User(user.email ?: " ", user.uid))
        } catch (e: Exception){
            AuthResult.Error(e)
        }
    }

}