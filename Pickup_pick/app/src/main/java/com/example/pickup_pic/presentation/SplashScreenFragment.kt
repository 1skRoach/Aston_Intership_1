package com.example.pickup_pic.presentation

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentSplashScreenBinding
import com.example.pickup_pic.databinding.FragmentThirdBinding
import kotlinx.coroutines.*


class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            startProgressbar()
            delay(2000)
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_authenticationFragment2)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
        }

        return binding.root
    }

    private fun startProgressbar() {
        binding.progressBar.max = 1000
        val value = 999
        ObjectAnimator.ofInt(binding.progressBar, "progress", value).setDuration(2000).start()
    }



    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}