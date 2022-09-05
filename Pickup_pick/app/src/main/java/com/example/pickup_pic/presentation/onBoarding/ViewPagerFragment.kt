package com.example.pickup_pic.presentation.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentViewPagerBinding
import com.example.pickup_pic.presentation.onBoarding.screens.FirstFragment
import com.example.pickup_pic.presentation.onBoarding.screens.SecondFragment
import com.example.pickup_pic.presentation.onBoarding.screens.ThirdFragment
import kotlinx.coroutines.launch

class ViewPagerFragment() : Fragment() {

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private val fragmentList = arrayListOf<Fragment>(
        FirstFragment(),
        SecondFragment(),
        ThirdFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)






        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.viewPager)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }




}