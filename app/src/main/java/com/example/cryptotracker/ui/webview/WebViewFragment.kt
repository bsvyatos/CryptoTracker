package com.example.cryptotracker.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.cryptotracker.databinding.CoinDetailsFragmentBinding
import com.example.cryptotracker.databinding.WebviewFragmentBinding
import com.example.cryptotracker.ui.details.CoinDetailsFragmentArgs
import com.example.cryptotracker.ui.details.CoinDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment: Fragment() {
    private val viewModel: WebViewModel by viewModels()
    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var binding: WebviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WebviewFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.websiteUrl = args.websiteUrl
        return binding.root
    }

    private fun setUpVisibility() {
        viewModel.isLoading.observe(viewLifecycleOwner, { ifLoading ->
            if(ifLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.webView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE
            }
        })
    }
}