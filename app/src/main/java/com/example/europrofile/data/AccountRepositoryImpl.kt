package com.example.europrofile.data

import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(val firestore: FirebaseFirestore): AccountRepository {

    override fun getUserInfo(result : (RequestResult<User>) -> Unit) {
        firestore.collection(FireBaseTags.USERS)
            .get()
            .addOnSuccessListener {
                val db = Firebase.database.reference
        }.addOnFailureListener{

            }
    }
}