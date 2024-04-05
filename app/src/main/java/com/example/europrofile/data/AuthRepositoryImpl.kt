package com.example.europrofile.data

import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth,
                                             private val firestore: FirebaseFirestore)  : AuthRepository {

    override suspend fun signInWithEmailPassword(email: String, password: String): RequestResult<User> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user
                ?: throw Exception("Ошибка входа")
            RequestResult.Success(User(user.uid , user.email?:""))

        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

    override suspend fun signUp(name: String, phoneNumber: String, email: String, password: String) : RequestResult<User> {
        return try {
            val authUser = auth.createUserWithEmailAndPassword(email, password).await().user ?: throw Exception("Ошибка регистрации")
            val user = User(id= authUser.uid, email = email, name = name, number = phoneNumber, password=password)
            val addingResult = addUser(user)

            if (addingResult is RequestResult.Error) {
                throw Exception("Ошибка добваления в базу данных")
            }
           RequestResult.Success(user)
        } catch (e: Exception){
            RequestResult.Error(e)
        }
    }

    private suspend fun addUser(user: User) : RequestResult<String> {
        return try {
            firestore.collection(FireBaseTags.USERS).document(user.id).set(
                user
            ).await()
            RequestResult.Success("Success")
        } catch (e: Exception) {
            RequestResult.Error(Exception("Ошибка добавления пользователя"))
        }
    }


}