package com.example.pickup_pic.presentation.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentProfileBinding
import com.example.pickup_pic.domain.models.profile.Profile
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProfileAdapterDelegate :
    AbsListItemAdapterDelegate<Profile.Photo, Profile.Photo, ProfileAdapterDelegate.PhotoViewHolder>() {

    class PhotoViewHolder(
        private val binding: FragmentProfileBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Profile.Photo) {
            binding.ivAvatar.load(item.urls.regular) {
                placeholder(R.drawable.photo_placeholder)
                error(R.drawable.photo_placeholder)
                crossfade(true)
                crossfade(200)
                transformations(CircleCropTransformation())
            }





        }
    }

    override fun isForViewType(
        item: Profile.Photo,
        items: MutableList<Profile.Photo>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): PhotoViewHolder =
        PhotoViewHolder(
            FragmentProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        item: Profile.Photo,
        holder: PhotoViewHolder,
        payloads: MutableList<Any>
    ) = holder.bind(item)
}