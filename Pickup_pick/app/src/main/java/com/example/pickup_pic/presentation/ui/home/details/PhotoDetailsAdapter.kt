package com.example.pickup_pic.presentation.ui.home.details

import androidx.recyclerview.widget.DiffUtil
import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PhotoDetailsAdapter(
    onLocationClick: (lat: Double, lng: Double) -> Unit
): AsyncListDifferDelegationAdapter<PhotoDetails>(FeedPhotoDetailsDiffUtilItemCallback()) {

    //work with complicated lists
    init {
        delegatesManager.addDelegate(
            PhotoDetailsAdapterDelegate(
                onLocationClick = onLocationClick,
            )
        )
    }

    class FeedPhotoDetailsDiffUtilItemCallback : DiffUtil.ItemCallback<PhotoDetails>() {
        override fun areItemsTheSame(
            oldItem: PhotoDetails,
            newItem: PhotoDetails
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PhotoDetails,
            newItem: PhotoDetails
        ): Boolean = oldItem == newItem
    }
}