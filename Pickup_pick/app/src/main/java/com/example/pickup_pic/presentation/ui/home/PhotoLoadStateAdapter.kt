package com.example.pickup_pic.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup_pic.databinding.LoadStateBinding

class PhotoLoadStateAdapter(
    private val onRetryButtonClick: () -> Unit
) : LoadStateAdapter<PhotoLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        private val binding: LoadStateBinding,
        onRetryButtonClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                onRetryButtonClick()
            }
        }

        fun bind(loadState: LoadState) = with(binding) {
            binding.loadingProgressBar.isVisible = loadState is LoadState.Loading
            binding.errorTextView.isVisible = loadState is LoadState.Error
            binding.retryButton.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            binding = LoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRetryButtonClick = onRetryButtonClick
        )
}