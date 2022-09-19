package com.example.pickup_pic.presentation.ui.home.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentPhotoDetailBinding
import com.example.pickup_pic.databinding.ItemUnsplashPhotoBinding
import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class PhotoDetailsAdapterDelegate(
    private val onLocationClick: (lat: Double, lng: Double) -> Unit,
) : AbsListItemAdapterDelegate<PhotoDetails, PhotoDetails, PhotoDetailsAdapterDelegate.FeedPhotoDetailsViewHolder>() {

    class FeedPhotoDetailsViewHolder(
        private val binding: FragmentPhotoDetailBinding,
        onLocationClick: (lat: Double, lng: Double) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var lat: Double? = null
        private var lng: Double? = null

        init {
            binding.locationTextView.setOnClickListener {
                if (lat != null && lng != null) {
                    onLocationClick(lat!!, lng!!)
                }
            }
        }

        fun bind(item: PhotoDetails) {
            lat = item.location.position.latitude
            lng = item.location.position.longitude

            binding.locationTextView.text =
                itemView.context.getString(
                    R.string.location,
                    item.location.country ?: "N/A", item.location.city ?: "N/A"
                )

            binding.tagTextView.text = itemView.context.getString(
                R.string.tag,
                item.tags.joinToString { tag -> "#${tag.title}" }
            )

            binding.madeWithTextView.text = itemView.context.getString(
                R.string.made_with,
                item.exif.make
            )

            binding.modelTextView.text = itemView.context.getString(
                R.string.model,
                item.exif.model
            )

            binding.exposureTextView.text = itemView.context.getString(
                R.string.exposure,
                item.exif.exposureTime
            )

            binding.apertureTextView.text = itemView.context.getString(
                R.string.aperture,
                item.exif.aperture
            )

            binding.focalLengthTextView.text = itemView.context.getString(
                R.string.focal_length,
                item.exif.focalLength
            )

            binding.isoTextView.text = itemView.context.getString(
                R.string.iso,
                item.exif.iso.toString()
            )

            binding.aboutTextView.text = itemView.context.getString(
                R.string.about,
                item.user.username,
                item.user.bio ?: "N/A"
            )

        }

    }

    override fun isForViewType(
        item: PhotoDetails,
        items: MutableList<PhotoDetails>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): FeedPhotoDetailsViewHolder =
        FeedPhotoDetailsViewHolder(
            binding = FragmentPhotoDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onLocationClick = onLocationClick
        )

    override fun onBindViewHolder(
        item: PhotoDetails,
        holder: FeedPhotoDetailsViewHolder,
        payloads: MutableList<Any>
    ) = holder.bind(item)


}