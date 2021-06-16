package com.example.cryptotracker.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.adapters.CoinsAdapter
import com.example.cryptotracker.adapters.CoinsLoadStateAdapter
import com.example.cryptotracker.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class MainFragment: Fragment() {
    private var mainCoinRecyclerView: RecyclerView? = null
    private var emptyListText: TextView? = null
    private var progressBar: ProgressBar? = null
    private var retryButton: Button? = null

    private val viewModel: MainViewModel by viewModels()
    private lateinit var coinsAdapter: CoinsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinsAdapter = CoinsAdapter(viewModel)
        mainCoinRecyclerView = view.findViewById(R.id.main_coin_recycler_view)
        emptyListText = view.findViewById(R.id.emptyList)
        progressBar = view.findViewById(R.id.progress_bar)
        retryButton = view.findViewById(R.id.retry_button)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mainCoinRecyclerView?.addItemDecoration(decoration)
        initAdapter()
        observeViewModel()
        setUpNavigation()
        retryButton?.setOnClickListener {
            coinsAdapter.retry()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getCoins().collectLatest {
                coinsAdapter.submitData(it)
            }
        }
    }

    private fun setUpNavigation() {
        viewModel.navigateToDetailsEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToAddNewTask(it)
        })
    }

    private fun navigateToAddNewTask(id: String) {
        val action = MainFragmentDirections
            .actionMainFragmentToCoinDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun initAdapter() {
        mainCoinRecyclerView?.adapter = coinsAdapter.withLoadStateHeaderAndFooter(
            header = CoinsLoadStateAdapter { coinsAdapter.retry() },
            footer = CoinsLoadStateAdapter { coinsAdapter.retry() }
        )

        coinsAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && coinsAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            mainCoinRecyclerView?.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            progressBar?.isVisible = loadState.mediator?.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            retryButton?.isVisible = loadState.mediator?.refresh is LoadState.Error
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Error: ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyListText?.visibility = View.VISIBLE
            mainCoinRecyclerView?.visibility = View.GONE
        } else {
            emptyListText?.visibility = View.GONE
            mainCoinRecyclerView?.visibility = View.VISIBLE
        }
    }
}