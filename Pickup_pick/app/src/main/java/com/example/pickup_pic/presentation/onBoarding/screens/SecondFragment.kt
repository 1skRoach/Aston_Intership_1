package com.example.pickup_pic.presentation.onBoarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {


    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.imageView.load(R.drawable.logo2)

        binding.nextTextView2.setOnClickListener {
            viewPager?.currentItem = 2
        }


        return binding.root
    }

}