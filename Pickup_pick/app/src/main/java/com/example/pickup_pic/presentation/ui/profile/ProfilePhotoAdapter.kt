package com.example.pickup_pic.presentation.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.bumptech.glide.Glide
import com.example.pickup_pic.databinding.FragmentProfileBinding
import com.example.pickup_pic.domain.models.profile.Profile
import com.nightlynexus.viewstatepageradapter.ViewStatePagerAdapter

class ProfilePhotoAdapter (
    private val photos: List<Profile.Photo>
) : ViewStatePagerAdapter() {

    override fun getCount(): Int = photos.size

    override fun createView(container: ViewGroup?, position: Int): View {

        val photo = photos[position]

        val binding = FragmentProfileBinding.inflate(
            LayoutInflater.from(container?.context),
            container,
            false
        )

        val view = binding.root



        binding.ivAvatar.load(photo.urls.regular)

        return view
    }
}