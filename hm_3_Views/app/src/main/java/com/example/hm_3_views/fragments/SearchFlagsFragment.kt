package com.example.hm_3_views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hm_3_views.databinding.FragmentSearchFlagsBinding
import kotlinx.coroutines.delay

class SearchFlagsFragment : Fragment() {

    private var _binding: FragmentSearchFlagsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFlagsBinding.inflate(inflater, container, false)

        val imageUrl = "https://picsum.photos/500"

        binding.searchPicButton.setOnClickListener {

            lifecycleScope.launchWhenStarted {
                binding.progressBar.visibility = View.VISIBLE
                delay(1000)
                Glide.with(requireContext())
                    .load(imageUrl)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.imageView)
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }
}