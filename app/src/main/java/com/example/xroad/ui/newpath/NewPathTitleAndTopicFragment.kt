package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathTitleAndTopicBinding

class NewPathTitleAndTopicFragment : Fragment() {
    private var _binding: FragmentNewPathTitleAndTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathTitleAndTopicBinding
            .inflate(inflater, container, false)
        val view = binding.root

        binding.button.setOnClickListener {
            findNavController().navigate(
                NewPathTitleAndTopicFragmentDirections.actionTitleAndTopicToDescription())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbar.title = getString(R.string.title_and_topic_label)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}