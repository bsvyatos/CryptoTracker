package com.example.cryptotracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.databinding.CoinDetailsFragmentBinding
import com.example.cryptotracker.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {
    private val args: CoinDetailsFragmentArgs by navArgs()
    private val viewModel: CoinDetailsViewModel by viewModels()
    private lateinit var binding: CoinDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CoinDetailsFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setUpEventListening()
        setUpVisibility()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(args.coinId)
    }

    private fun getData(coinId: String) {
        viewModel.setCoinDataId(coinId)
    }

    private fun setUpEventListening() {
        viewModel.navigateToUrl.observe(viewLifecycleOwner, EventObserver {
            val action = CoinDetailsFragmentDirections
                .actionCoinDetailsFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        })
    }

    private fun setUpVisibility() {
        viewModel.coinData.observe(viewLifecycleOwner, {
            if(it == null) {
                binding.referenceGroup.visibility = View.GONE
            } else if(it.urls?.twitter == null) {
                binding.twitterLink.visibility = View.GONE
            } else if(it.urls?.website == null) {
                binding.websiteLink.visibility = View.GONE
            }
        })
    }

}