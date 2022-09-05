package com.example.pickup_pic.presentation.host_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentAuthenticationBinding
import com.example.pickup_pic.databinding.FragmentLoggedBinding

class LoggedFragment : Fragment() {

    private var _binding: FragmentLoggedBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fcvNavHostFragment)
                ?: error("Fragment not found")

        navHostFragment.findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        with(binding.bottomNavigation) {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                item.onNavDestinationSelected(navController)
            }
        }
    }

}