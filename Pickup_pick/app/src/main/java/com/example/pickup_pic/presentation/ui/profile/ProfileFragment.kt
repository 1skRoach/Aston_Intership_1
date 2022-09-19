package com.example.pickup_pic.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentProfileBinding
import com.example.pickup_pic.domain.models.profile.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfileData()
        bindViewModel()
        initBackButtonNav()
    }

    private fun getProfileData() {
        lifecycleScope.launch {
            viewModel.getProfileData()
        }
    }

    private fun initPhotoPager(photos: List<Profile.Photo>) {
        with(binding.vpPhotos) {
            adapter = ProfilePhotoAdapter(photos)

            offscreenPageLimit = 1

            //  setPageTransformer(false, ProfilePhotoTransformer())
        }
    }

    private fun bindProfileData(profile: Profile) {
        binding.ivAvatar.load(profile.image.large) {
            placeholder(R.drawable.photo_placeholder)
            error(R.drawable.photo_placeholder)
            crossfade(true)
            crossfade(200)
            transformations(CircleCropTransformation())
        }

        binding.tvName.text = profile.name
        binding.tvUsername.text = profile.username
        binding.tvLocation.text = profile.location
        binding.tvEmail.text = profile.email
        binding.tvPhotoCount.text = getString(R.string.photos, profile.totalPhotos)
        binding.tvLikeCount.text = getString(R.string.likes, profile.totalLikes)
        binding.tvCollectionCount.text = getString(R.string.collections, profile.totalCollections)
    }

    private fun toggleViewsVisibility(isVisible: Boolean) {
        listOf(
            binding.ivAvatar,
            binding.tvName,
            binding.tvUsername,
            binding.tvLocation,
            binding.tvEmail,
            binding.tvPhotoCount,
            binding.tvLikeCount,
            binding.ivEmail,
            binding.ivLocation,
            binding.ivUsername,
            binding.vpPhotos,
            binding.tvCollectionCount,
            binding.viewLine,
            binding.viewLine2,
            binding.viewLine3,

            ).forEach { view ->
            view.isVisible = !isVisible
        }
        binding.pbLoading.isVisible = isVisible
    }


private fun bindViewModel() {
    viewModel.dataState.observe(viewLifecycleOwner) { state ->
        when (state) {
            is ProfileState.Loading -> {
                toggleViewsVisibility(state.isLoading)
            }
            is ProfileState.Success -> {
                initPhotoPager(state.profile.photos)
                bindProfileData(state.profile)
            }
            is ProfileState.Error -> {

            }
            is ProfileState.Cancellation -> {

            }
        }
    }
}

    private fun initBackButtonNav() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                        ?.let { nav ->
                            nav.selectedItemId = R.id.homeFragment
                        } ?: error("View not found")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.cancelScopeChildrenJobs()
    }

}