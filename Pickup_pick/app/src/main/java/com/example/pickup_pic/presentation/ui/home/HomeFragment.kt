package com.example.pickup_pic.presentation.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentHomeBinding
import com.example.pickup_pic.databinding.FragmentLoggedBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        with(binding.toolbar) {
            title = getText(R.string.app_name)

            inflateMenu(R.menu.feed_toolbar_menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.orderByLatestAction -> {
                        showSnackBar("tapped 1")
                      //  getData(ORDER_BY_LATEST)
                        true
                    }
                    R.id.orderByOldestAction -> {
                        showSnackBar("tapped 2")
                        //   getData(ORDER_BY_OLDEST)
                        true
                    }
                    R.id.orderByPopularAction -> {
                        showSnackBar("tapped 3")
                        //   getData(ORDER_BY_POPULAR)
                        true
                    }
                    else -> {
                    //    val directions = FeedFragmentDirections.actionFeedToSearch()
                    //    findNavController().navigate(directions)
                        true
                    }
                }
            }

        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar
            .make(
                requireView(),
                msg,
                Snackbar.LENGTH_LONG
            ).show()
    }



}