package com.example.europrofile.data

import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): AccountRepository {

    override fun getUserInfo(uid: String, result : (RequestResult<User>) -> Unit) {
        firestore.collection(FireBaseTags.USERS).document(uid)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                if (user!=null) {
                    result.invoke(RequestResult.Success(user))
                } else result.invoke(RequestResult.Error(Exception("Ошибка получения пользователя")))
        }.addOnFailureListener{
            result.invoke(
                RequestResult.Error(it)
            )
        }
    }
}