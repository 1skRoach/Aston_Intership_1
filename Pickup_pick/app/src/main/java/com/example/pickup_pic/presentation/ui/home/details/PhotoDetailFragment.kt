package com.example.pickup_pic.presentation.ui.home.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.pickup_pic.R
import com.example.pickup_pic.data.network.status_tracker.NetworkStatus
import com.example.pickup_pic.databinding.FragmentHomeBinding
import com.example.pickup_pic.databinding.FragmentPhotoDetailBinding
import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import com.example.pickup_pic.presentation.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    private val detailsViewModel: PhotoDetailViewModel by viewModels()


    private val args by navArgs<PhotoDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFeedPhotoDetails(args.id)
        observeDataFetching()
        setupToolbar()
        handleToolbarNavigation()
        observeNetworkConnection()
    }

    //method in viewModel ->
    private fun getFeedPhotoDetails(id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            detailsViewModel.getFeedPhotoDetails(id)
        }
    }

    private fun observeNetworkConnection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.networkStatus.collect { status ->
                    when (status) {
                        NetworkStatus.Available -> {
                            binding.noConnectionTextView.isVisible = false
                            hideViews(true)
                        }
                        NetworkStatus.Unavailable -> {
                            binding.noConnectionTextView.isVisible = true
                            hideViews(false)
                        }
                    }
                }
            }
        }
    }

    private fun hideViews(isHidden: Boolean) {
        listOf(
                binding.aboutTextView,
                binding.picImageView,
                binding.avatar,
                binding.nickname,
                binding.name,
                binding.locationImageView,
                binding.locationTextView,
                binding.tagTextView,
                binding.madeWithTextView,
                binding.modelTextView,
                binding.exposureTextView,
                binding.apertureTextView,
                binding.focalLengthTextView,
                binding.isoTextView,
                binding.aboutTextView,
        ).forEach { view ->
            view.isVisible = isHidden
        }
    }

    //state check for all states and setting visibility for necessary views
    private fun observeDataFetching() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.feedDetailsState.collect { state ->
                    when (state) {
                        is PhotoDetailsState.Loading -> {
                            binding.loadingProgressBar.isVisible = true
                        }
                        is PhotoDetailsState.Success -> {
                            binding.loadingProgressBar.isVisible = false
                            binding.locationImageView.isVisible = true

                            bindFetchedData(state.data)
                        }
                        is PhotoDetailsState.Error -> {
                            binding.loadingProgressBar.isVisible = false
                            //   state.error.message?.let { showInfo(it) }
                            Timber.d("${state.error}")
                        }
                    }
                }
            }
        }
    }

    private fun bindFetchedData(details: PhotoDetails) {
        //set image with coil
        binding.picImageView.load(details.url.raw) {
            placeholder(R.drawable.photo_placeholder)
            error(R.drawable.photo_placeholder)
            crossfade(true)
            crossfade(200)
            transformations(RoundedCornersTransformation(20f))
        }

        //set avatar with coil
        binding.avatar.load(details.user.profileImage.medium) {
            placeholder(R.drawable.photo_placeholder)
            error(R.drawable.photo_placeholder)
            crossfade(true)
            crossfade(200)
            transformations(CircleCropTransformation())
        }

        binding.name.text = details.user.name
        binding.nickname.text = getString(R.string.username, details.user.username)

        //lambda for parsing lat &lng
        val onLocationClick: (lat: Double, lng: Double) -> Unit = { lat, lng ->
            val locationUri = Uri.parse("geo: $lat,$lng")
            showLocationInMap(locationUri)
        }

        //getting lat & lng
        val lat = details.location.position.latitude
        val lng = details.location.position.longitude

        //send lat & lng to click and check for nullable
        binding.locationImageView.setOnClickListener {
            if (lat != null && lng != null) {
                onLocationClick(lat, lng)
            }
        }

        // set the location info
        binding.locationTextView.text = getString(
            R.string.location,
            details.location.country ?: "N/A", details.location.city ?: "N/A"
        )

        binding.tagTextView.text = getString(R.string.tag, details.tags.joinToString { tag ->
            "#${tag.title ?: "N/A"}"
        })

        binding.tagTextView.text = getString(
            R.string.tag,
            details.tags.joinToString { tag -> "#${tag.title}" }
        )

        binding.madeWithTextView.text = getString(
            R.string.made_with,
            details.exif.make
        )

        binding.modelTextView.text = getString(
            R.string.model,
            details.exif.model
        )

        binding.exposureTextView.text = getString(
            R.string.exposure,
            details.exif.exposureTime
        )

        binding.apertureTextView.text = getString(
            R.string.aperture,
            details.exif.aperture
        )

        binding.focalLengthTextView.text = getString(
            R.string.focal_length,
            details.exif.focalLength
        )

        binding.isoTextView.text = getString(
            R.string.iso,
            details.exif.iso.toString()
        )

        binding.aboutTextView.text = getString(
            R.string.about,
            details.user.username,
            details.user.bio ?: "N/A"
        )
    }

    //intent for opening the map
    private fun showLocationInMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        if (activity?.packageManager != null) {
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            title = getString(R.string.feed_photo)

            inflateMenu(R.menu.profile_menu)

        }
    }

    private fun handleToolbarNavigation() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}