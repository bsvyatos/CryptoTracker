package com.example.cryptotracker.ui.main

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.cryptotracker.R
import com.example.cryptotracker.adapters.CoinsAdapter
import com.example.cryptotracker.adapters.CoinsLoadStateAdapter
import com.example.cryptotracker.databinding.MainFragmentBinding
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var coinsAdapter: CoinsAdapter
    private lateinit var binding: MainFragmentBinding
    private var coinQueryJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.mainCoinRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(decoration)
        }
        setHasOptionsMenu(true)
        coinsAdapter = CoinsAdapter(viewModel)
        initAdapter()
        setUpNavigation()
        setEventObservers()
        setSwipeRefresh()
        getData()
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_sort -> {
                showSortMenu()
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    private fun getData() {
        viewModel.getLatestData()
    }

    private fun showSortMenu() {
        val view = activity?.findViewById<View>(R.id.menu_sort) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.coin_sorting_menu, menu)

            menu.getItem(viewModel.coinsSort.ordinal).isChecked = true
            setOnMenuItemClickListener {
                viewModel.changeSorting(it)
                true
            }
            show()
        }
    }

    private fun setSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            coinsAdapter.refresh()
        }
    }

    private fun setEventObservers() {
        viewModel.retryDataEvent.observe(viewLifecycleOwner, EventObserver {
            coinsAdapter.retry()
        })

        viewModel.newPagingDataEvent.observe(viewLifecycleOwner, EventObserver {
            observePagingDataFlow(it)
        })
    }

    private fun observePagingDataFlow(flow: Flow<PagingData<CoinData>>) {
        coinQueryJob?.cancel()
        coinQueryJob = lifecycleScope.launch {
            flow.collectLatest {
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
        binding.mainCoinRecyclerView.adapter = coinsAdapter.withLoadStateFooter(
            footer = CoinsLoadStateAdapter { coinsAdapter.retry() }
        )

        coinsAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && coinsAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Show the list if refresh succeeds or we have some items to show
            binding.mainCoinRecyclerView.isVisible =
                loadState.mediator?.refresh is LoadState.NotLoading || !isListEmpty
            // Show loading spinner during initial load or refresh. Don't show it if we initiated refresh with swipe
            binding.progressBar.isVisible =
                loadState.mediator?.refresh is LoadState.Loading && !binding.swipeRefreshLayout.isRefreshing
            // Show the retry state if initial load or refresh fails and we have no items to show
            binding.retryButton.isVisible =
                loadState.mediator?.refresh is LoadState.Error && isListEmpty
            // Hide swipeRefreshLayout if we're not loading
            if (loadState.mediator?.refresh is LoadState.NotLoading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
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
            binding.emptyList.visibility = View.VISIBLE
            binding.mainCoinRecyclerView.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.mainCoinRecyclerView.visibility = View.VISIBLE
        }
    }
}