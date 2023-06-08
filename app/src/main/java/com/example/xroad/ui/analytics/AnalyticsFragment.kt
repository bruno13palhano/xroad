package com.example.xroad.ui.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.example.xroad.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_analytics, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.title = getString(R.string.analytics_label)

        binding.uiEvents = this

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToPathCharts() {
        findNavController().navigate(
            AnalyticsFragmentDirections.actionAnalyticsToPathCharts())
    }
}