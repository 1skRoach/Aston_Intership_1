package com.example.pickup_pic.domain.repository

import androidx.paging.PagingData
import com.example.pickup_pic.data.db.contracts.entities.PhotoEntity
import com.example.pickup_pic.domain.models.photo.Photo
import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(order: String, currentOrder: String): Flow<PagingData<Photo>>
    suspend fun getFeedPhotoDetailsById(id: String): PhotoDetails
    suspend fun insertAllPhotos(photos: List<PhotoEntity>)
    suspend fun updatePhoto(id: String, isLiked: Boolean, likeCount: Int)
    fun getSearchResult(query: String): Flow<PagingData<Photo>>
}