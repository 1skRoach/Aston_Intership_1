package com.example.pickup_pic.presentation.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pickup_pic.R
import com.example.pickup_pic.data.network.status_tracker.NetworkStatus
import com.example.pickup_pic.databinding.FragmentHomeBinding
import com.example.pickup_pic.databinding.FragmentLoggedBinding
import com.example.pickup_pic.utils.autoCleaned
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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
        initPhotoList()
        getData(viewModel.prevOrderBy)
        observeAdapterLoadingState()
        observeData()
        initListRefresh()
        observeNetworkConnection()
    }

    private val feedAdapter by autoCleaned {
        PhotoAdapter(
            onItemClick = { photoId ->
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPhotoDetailFragment(photoId))
            }
        )
    }

    private fun initListRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView2.isVisible = true
            feedAdapter.refresh()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.feedState.collectLatest { state ->
                    when (state) {
                        is HomeState.Success -> {
                            binding.refreshLayout.isRefreshing = false
                            feedAdapter.submitData(state.data)
                        }
                        is HomeState.Error -> {
                            binding.refreshLayout.isRefreshing = false
                            binding.progressBar.isVisible = false
                            Timber.d("${state.error}")
                        }
                    }
                }
            }
        }
    }

    private fun observeNetworkConnection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.networkStatus.collect { status ->
                    when (status) {
                        NetworkStatus.Available -> {
                            binding.tvConnection.isVisible = false
                            binding.recyclerView2.isVisible = true
                            // retry after connection re-established
                            feedAdapter.retry()
                        }
                        NetworkStatus.Unavailable -> {
                            binding.recyclerView2.isVisible = false
                            binding.tvConnection.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun getData(order: String) {
        lifecycleScope.launch {
            viewModel.getFeedPhotos(order)
        }
    }

    private fun initPhotoList() {
        with(binding.recyclerView2) {
            val feedLoadStateAdapter = PhotoLoadStateAdapter(
                onRetryButtonClick = { feedAdapter.retry() }
            )
            val concatAdapter = feedAdapter.withLoadStateFooter(
                footer = feedLoadStateAdapter
            )

            adapter = concatAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            addItemDecoration(PhotoOffsetDecoration(requireContext()))

        }
    }

    private fun observeAdapterLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            feedAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
    }


    private fun setUpToolbar() {
        with(binding.toolbar) {
            title = getText(R.string.app_name)

            inflateMenu(R.menu.feed_toolbar_menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.orderByLatestAction -> {
                        showSnackBar("tapped 1")
                          getData(ORDER_BY_LATEST)
                        true
                    }
                    R.id.orderByOldestAction -> {
                        showSnackBar("tapped 2")
                           getData(ORDER_BY_OLDEST)
                        true
                    }
                    R.id.orderByPopularAction -> {
                        showSnackBar("tapped 3")
                           getData(ORDER_BY_POPULAR)
                        true
                    }
                    else -> {
                            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ORDER_BY_LATEST = "latest"
        private const val ORDER_BY_OLDEST = "oldest"
        private const val ORDER_BY_POPULAR = "popular"
    }
}