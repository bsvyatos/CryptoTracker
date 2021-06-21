package com.example.cryptotracker.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.WebviewFragmentBinding
import com.example.cryptotracker.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private val viewModel: WebViewModel by viewModels()
    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var binding: WebviewFragmentBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WebviewFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.websiteUrl = args.websiteUrl
        binding.webView.settings.javaScriptEnabled = true
        setUpVisibility()
        setEventObservers()
        return binding.root
    }

    private fun setUpVisibility() {
        viewModel.isLoading.observe(viewLifecycleOwner, { ifLoading ->
            if (ifLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.webView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE
            }
        })
    }

    private fun setEventObservers() {
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(
                requireContext(),
                it ?: getString(R.string.webview_error),
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}