package com.example.hm_5_fragments.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hm_5_fragments.R
import com.example.hm_5_fragments.adapter.ContactAdapter
import com.example.hm_5_fragments.databinding.FragmentContactBinding
import com.example.hm_5_fragments.databinding.FragmentContactListBinding

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        val args: ContactFragmentArgs by navArgs()

            binding.lastName.text = args.lastName
            binding.firstName.text = args.firstName
            binding.contactNumber.text = args.contactNumber
        binding.imageView.load(args.imageView)

        return binding.root
    }


}