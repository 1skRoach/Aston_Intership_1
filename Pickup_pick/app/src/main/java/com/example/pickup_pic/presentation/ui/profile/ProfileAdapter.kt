package com.example.pickup_pic.presentation.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.example.pickup_pic.domain.models.profile.Profile
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProfileAdapter :
    AsyncListDifferDelegationAdapter<Profile.Photo>(PhotoItemDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ProfileAdapterDelegate())
    }

    class PhotoItemDiffUtilCallback : DiffUtil.ItemCallback<Profile.Photo>() {
        override fun areItemsTheSame(oldItem: Profile.Photo, newItem: Profile.Photo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Profile.Photo, newItem: Profile.Photo): Boolean =
            oldItem == newItem
    }
}