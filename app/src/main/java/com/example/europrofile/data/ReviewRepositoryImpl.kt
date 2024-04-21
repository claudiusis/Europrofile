package com.example.europrofile.data

import android.net.Uri
import android.util.Log
import com.example.europrofile.core.FireBaseTags
import com.example.europrofile.domain.Review
import com.example.europrofile.domain.ReviewRepository
import com.example.europrofile.domain.User
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    override suspend fun getReviewList(): RequestResult<List<ViewReview>> {
        return try {
            val list = firestore.collection(FireBaseTags.REVIEWS_STORAGE).get().await()
            val array = mutableListOf<ViewReview>()
            for (doc in list){
                val review = doc.toObject(Review::class.java)
                val user = firestore.collection(FireBaseTags.USERS).document(review.idOfUSer).get().await().toObject(User::class.java)
                    ?: throw Exception("Ошибка получения владельца отзыва")

                array.add(
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
            }
            RequestResult.Success(array)
        } catch (e: Exception){
            Log.i("QWERTY", e.message.toString())
            RequestResult.Error(Exception("Ошибка получения списка"))
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