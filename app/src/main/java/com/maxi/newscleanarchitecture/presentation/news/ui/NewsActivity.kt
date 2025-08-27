package com.maxi.newscleanarchitecture.presentation.news.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maxi.newscleanarchitecture.common.Constants.UNKNOWN_ERROR
import com.maxi.newscleanarchitecture.databinding.ActivityNewsBinding
import com.maxi.newscleanarchitecture.presentation.base.UiState
import com.maxi.newscleanarchitecture.presentation.news.adapter.NewsAdapter
import com.maxi.newscleanarchitecture.presentation.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        setupUi()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this@NewsActivity)[NewsViewModel::class]
    }

    private fun setupUi() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = this@NewsActivity.adapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@NewsActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        observeDataAndUpdateUi()
    }

    private fun observeDataAndUpdateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            adapter.setData(state.data)
                            updateProgressBarVisibility(false)
                        }

                        is UiState.ApiError -> {
                            showError(state.errorMessage)
                        }

                        is UiState.DatabaseError -> {
                            showError(state.errorMessage)
                        }

                        is UiState.IOError,
                        is UiState.UnknownError -> {
                            showError(UNKNOWN_ERROR)
                        }

                        is UiState.Loading -> {
                            Log.d(NewsActivityTAG, "observeDataAndUpdateUi: Loading...")
                            updateProgressBarVisibility(true)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBarVisibility(isVisible: Boolean) {
        binding.pbNews.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(
            binding.root,
            errorMessage,
            Snackbar.LENGTH_LONG
        ).show()
        updateProgressBarVisibility(false)
    }
}

const val NewsActivityTAG = "NewsActivity"