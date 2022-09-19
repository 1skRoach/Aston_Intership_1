package com.example.pickup_pic.data.api

import com.example.pickup_pic.domain.models.photo.Photo
import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import com.example.pickup_pic.domain.models.profile.Profile
import com.example.pickup_pic.domain.search.SearchResponse
import retrofit2.http.*

interface UnsplashApi {
    @GET("/me")
    suspend fun getCurrentProfile(): Profile

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): List<Photo>

    @GET("/photos/{id}")
    suspend fun getFeedPhotoDetails(
        @Path("id") id: String
    ): PhotoDetails

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): SearchResponse
}