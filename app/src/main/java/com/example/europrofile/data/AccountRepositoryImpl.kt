package com.example.europrofile.data

import android.net.Uri
import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore, private val storage: StorageReference): AccountRepository {

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

    private suspend fun loadImg(uri : Uri): RequestResult<Uri>{
        return try {
            val reqUri = storage.child(FireBaseTags.USER_PHOTO+"/${uri.lastPathSegment}")
                .putFile(uri).await()
                .storage.downloadUrl.await()
            RequestResult.Success(reqUri)
        } catch (e: Exception){
            RequestResult.Error(Exception("Ошибка загрузки данных пользователя"))
        }
    }

    override suspend fun changeInfo(user: User, type: String): RequestResult<User> {
        return try {
            when (type) {
                FireBaseTags.COUNT_OF_REVIEWS_CHANGES -> firestore.collection(FireBaseTags.USERS).document(user.id).update("countOfReviews",
                    user.countOfReviews
                )
                FireBaseTags.LIKES -> {
                    firestore.collection(FireBaseTags.USERS).document(user.id).update("listOfFavourites", user.listOfFavourites)
                }
                else -> {
                    if (user.imgUri?.isNotEmpty()==true){
                        val result = withContext(Dispatchers.IO){
                            loadImg(Uri.parse(user.imgUri))
                        }
                        if (result is RequestResult.Error){
                            throw Exception("Load exception")
                        } else {
                            user.imgUri = (result as RequestResult.Success).data.toString()

                            firestore.collection(FireBaseTags.USERS).document(user.id).set(
                                user
                            ).await()
                        }

                    }
                }
            }
            RequestResult.Success(user)
        } catch (e: Exception){
            RequestResult.Error(Exception("Не удалось изменпить данные пользователя"))
        }

    }

    override suspend fun subscribeToUser(uid: String) : Flow<RequestResult<User>> =
        callbackFlow {
            val document = firestore.collection(FireBaseTags.USERS).document(uid)
            val listener = document.addSnapshotListener { value, error ->

                error?.let {
                    return@addSnapshotListener
                }

                try {
                    val user = value?.toObject(User::class.java) ?: throw Exception("Error of db")
                    trySend(RequestResult.Success(user))
                } catch (e : Exception) {
                    trySend(RequestResult.Error(e))
                }
            }
            awaitClose {
                listener.remove()
            }
        }.flowOn(Dispatchers.IO)
}