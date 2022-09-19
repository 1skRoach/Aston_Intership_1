package com.example.pickup_pic.data.repository

import android.content.Context
import androidx.paging.*
import com.example.pickup_pic.data.Db
import com.example.pickup_pic.data.api.UnsplashApi
import com.example.pickup_pic.data.db.contracts.entities.PhotoEntity
import com.example.pickup_pic.data.db.contracts.mappers.LinkEntityToLinkMapper
import com.example.pickup_pic.data.db.contracts.mappers.UrlEntityToUrlMapper
import com.example.pickup_pic.data.db.contracts.mappers.UserLinkEntityToUserLinkMapper
import com.example.pickup_pic.data.db.contracts.mappers.UserProfileImageEntityToUserProfileImageMapper
import com.example.pickup_pic.data.repository.paging_source.SearchPagingSource
import com.example.pickup_pic.domain.models.photo.Photo
import com.example.pickup_pic.domain.models.user.User
import com.example.pickup_pic.domain.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosRepositoryImpl@Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val db: Db,
  //  private val context: Context,
    private val dispatcherIo: CoroutineDispatcher
) : PhotosRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPhotos(
        order: String,
        currentOrder: String
    ): Flow<PagingData<Photo>> {
        val pagingSourceFactory = {
            db.photosDao().getPagingSource()
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = PhotosRemoteMediator(
                api = unsplashApi,
                order = order,
                currentOrder = currentOrder,
                db = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                mapPagingData(pagingData)
            }
    }

    private fun mapPagingData(pagingData: PagingData<PhotoEntity>) =
        pagingData.map { photoEntity ->

            /**
             * Getting photo and user relation from database.
             */
            val userAndPhotoEntity = db.userDao()
                .getUserAndPhotoWithUserId(photoEntity.userId)
                ?: error("user with id = ${photoEntity.userId} not found")

            /**
             * Getting user.
             */
            val userEntity = userAndPhotoEntity.user

            /**
             * Getting user profile image from database and mapping to POJO.
             */
            val userProfileImageEntity = db.userProfileImageDao()
                .getUserProfileImageWithId(userEntity.userProfileImageId)
                ?: error("user profile image with id = ${photoEntity.userId} not found")
            val userProfileImage =
                UserProfileImageEntityToUserProfileImageMapper().map(userProfileImageEntity)

            /**
             * Getting user link from database and mapping to POJO.
             */
            val userLinkEntity = db.userLinkDao()
                .getUserLinkWithId(userEntity.userLinkId)
                ?: error("user link with id = ${photoEntity.userId} not found")
            val userLink = UserLinkEntityToUserLinkMapper().map(userLinkEntity)

            /**
             * Mapping user to POJO.
             */
            val user = User(
                id = userEntity.id,
                username = userEntity.username,
                name = userEntity.name,
                firstName = userEntity.firstName,
                lastName = userEntity.lastName,
                instagramUsername = userEntity.instagramUsername,
                twitterUsername = userEntity.twitterUsername,
                portfolioUrl = userEntity.portfolioUrl,
                bio = userEntity.bio,
                location = userEntity.location,
                totalLikes = userEntity.totalLikes,
                totalPhotos = userEntity.totalPhotos,
                totalCollections = userEntity.totalCollections,
                profileImage = userProfileImage,
                link = userLink
            )

            /**
             * Getting photo url from database and mapping to POJO.
             * Url id is the same as photo id.
             */
            val urlEntity = db.urlDao()
                .getUrlWithId(photoEntity.id)
                ?: error("url with id = ${photoEntity.id} not found")
            val url = UrlEntityToUrlMapper().map(urlEntity)

            /**
             * Getting photo link from database and mapping to POJO.
             * Link id is the same as photo id.
             */
            val linkEntity = db.linkDao()
                .getLinkWithId(photoEntity.id)
                ?: error("link with id = ${photoEntity.id} not found")
            val link = LinkEntityToLinkMapper().map(linkEntity)

            /**
             * Finally mapping photo to POJO for later pass it to paging adapter.
             */
            Photo(
                id = photoEntity.id,
                createdAt = photoEntity.createdAt,
                updatedAt = photoEntity.updatedAt,
                width = photoEntity.width,
                height = photoEntity.height,
                color = photoEntity.color,
                blurHash = photoEntity.blurHash,
                likes = photoEntity.likes,
                likedByUser = photoEntity.likedByUser,
                description = photoEntity.description,
                user = user,
                url = url,
                link = link
            )
        }

    override suspend fun getFeedPhotoDetailsById(id: String) =
        unsplashApi.getFeedPhotoDetails(id)

    override suspend fun insertAllPhotos(photos: List<PhotoEntity>) =
        withContext(dispatcherIo) {
            db.photosDao().insertAllPhotos(photos)
        }

    override suspend fun updatePhoto(id: String, isLiked: Boolean, likeCount: Int) =
        withContext(dispatcherIo) {
            db.photosDao().updatePhoto(id, isLiked, likeCount)
        }

    override fun getSearchResult(query: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    api = unsplashApi,
                    query = query,
                    page = STARTING_PAGE_INDEX,
                    pageSize = PAGE_SIZE,
                    orderBy = ORDER_BY
                )
            }
        ).flow

    companion object {
        // default page value = 1
        private const val STARTING_PAGE_INDEX = 1

        // default per_page value = 10
        const val PAGE_SIZE = 10

        // default order_by value for search = relevant
        private const val ORDER_BY = "latest"
    }

}