package com.example.europrofile.data

import android.net.Uri
import android.util.Log
import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.Review
import com.example.europrofile.domain.ReviewRepository
import com.example.europrofile.domain.User
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore, private val storage: StorageReference): ReviewRepository {


    private suspend fun loadImages(imgList: List<Uri>, id: String): RequestResult<List<Uri>> {
        return try {
            val resultList = withContext(Dispatchers.IO) {
                imgList.map { img ->
                    async {
                        storage.child(FireBaseTags.REVIEWS_STORAGE+"/$id/${img.lastPathSegment}")
                            .putFile(img)
                            .await()
                            .storage
                            .downloadUrl
                            .await()
                    }
                }.awaitAll()
            }
            RequestResult.Success(resultList)
        } catch (e: Exception){
            RequestResult.Error(Exception("Не удалось загрузить картинки"))
        }
    }

    override suspend fun subscribePostChanges() : Flow<RequestResult<List<ViewReview>>> =
        callbackFlow {
            val collection = firestore.collection(FireBaseTags.REVIEWS_STORAGE).orderBy("date", Query.Direction.DESCENDING)
            val listener = collection.addSnapshotListener { value, error ->
                error?.let {
                    Log.d("QWERTY", "Error with listener")
                    return@addSnapshotListener
                }

                value?.let {

                    CoroutineScope(Dispatchers.IO).launch {

                        val list = mutableListOf<ViewReview>()

                        value.documents.forEach {


                            val review = it.toObject(Review::class.java)
                            val user = firestore.collection(FireBaseTags.USERS)
                                .document(review?.idOfUSer ?: "").get().await()
                                .toObject(User::class.java)
                            list.add(
                                ViewReview(
                                    review?.id,
                                    review?.idOfUSer,
                                    user?.name,
                                    user?.imgUri,
                                    review?.date,
                                    review?.imageList ?: listOf(),
                                    review?.description,
                                    review?.listOfUserLikes ?: mutableListOf(),
                                    review?.listOfUserDisLikes ?: mutableListOf()
                                )
                            )

                        }

                        trySend(RequestResult.Success(list))
                    }
                }
            }
            awaitClose {
                listener.remove()
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getReviewList(): Flow<RequestResult<ViewReview>> = flow {
        try {
            val list = firestore.collection(FireBaseTags.REVIEWS_STORAGE).orderBy("date", Query.Direction.DESCENDING).get().await()
            for (doc in list){
                val review = doc.toObject(Review::class.java)
                val user = firestore.collection(FireBaseTags.USERS).document(review.idOfUSer).get().await().toObject(User::class.java)
                    ?: throw Exception("Ошибка получения владельца отзыва")

                Log.d("QWERTY", user.toString())

                emit(
                    RequestResult.Success(
                        ViewReview(
                            review.id,
                            user.id,
                            user.name,
                            user.imgUri,
                            review.date,
                            review.imageList,
                            review.description, review.listOfUserLikes,
                            review.listOfUserDisLikes
                        )
                    )
                )
            }
        } catch (e: Exception){
            emit(RequestResult.Error(Exception("Ошибка получения списка")))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateLike(data: ViewReview) : RequestResult<String> {
        return try {

            val doc = firestore.collection(FireBaseTags.REVIEWS_STORAGE).document(data.id?:"")
            val res = doc.update(
                "listOfUserLikes", data.listOfUserLikes,
                "listOfUserDisLikes", data.listOfUserDisLikes
            ).await() ?: throw Exception("Ошибка изменения лайка")

            RequestResult.Success("It's OK")

        } catch (e: Exception){
            RequestResult.Error(Exception("Ошибка изменения лайка"))
        }

    }

    override suspend fun loadReview(review: Review): RequestResult<Review> {
        return try {
            val uriList : MutableList<Uri> = mutableListOf()
            val loadResult = withContext(Dispatchers.IO) {
                review.imageList.forEach { uriList.add(Uri.parse(it)) }
                loadImages(uriList, review.id.toString())
            }
            if (loadResult is RequestResult.Error){
                throw loadResult.e
            } else {
                val stringUri : MutableList<String> = mutableListOf()
                (loadResult as RequestResult.Success).data.forEach { stringUri.add(it.toString()) }
                review.imageList = stringUri
                firestore.collection(FireBaseTags.REVIEWS_STORAGE).document(review.id.toString()).set(
                    review
                ).await()
            }

            RequestResult.Success(review)
        } catch (e: Exception){
            RequestResult.Error(Exception("Не удалось сохранить отзыв"))
        }
    }
}