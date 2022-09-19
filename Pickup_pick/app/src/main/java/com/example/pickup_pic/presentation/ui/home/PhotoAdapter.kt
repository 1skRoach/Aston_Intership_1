package com.example.pickup_pic.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.ItemUnsplashPhotoBinding
import com.example.pickup_pic.domain.models.photo.Photo

class PhotoAdapter(
    private val onItemClick: (id: String) -> Unit
) : PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoComparator()) {

    inner class PhotoViewHolder(
        private val binding: ItemUnsplashPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) {

            binding.imageView.load(item.url.regular){
                placeholder(R.drawable.photo_placeholder)
                error(R.drawable.photo_placeholder)
                crossfade(true)
                crossfade(200)
                transformations(RoundedCornersTransformation(40f))
            }

            binding.avatar.load(item.user.profileImage.medium){
                placeholder(R.drawable.photo_placeholder)
                error(R.drawable.photo_placeholder)
                crossfade(true)
                crossfade(200)
                transformations(CircleCropTransformation())
            }

            binding.root.setOnClickListener {
                onItemClick(item.id)
            }

            binding.name.text = item.user.name
            binding.nickname.text =
                itemView.resources.getString(R.string.username, item.user.username)


        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            binding = ItemUnsplashPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    class PhotoComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem
    }
}