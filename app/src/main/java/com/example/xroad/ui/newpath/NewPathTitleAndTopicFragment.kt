package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.xroad.databinding.FragmentNewPathTitleAndTopicBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPathTitleAndTopicFragment : Fragment() {
    private var _binding: FragmentNewPathTitleAndTopicBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPathViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathTitleAndTopicBinding
            .inflate(inflater, container, false)
        val view = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.title.collect {
                        binding.title.setText(it)
                    }
                }
                launch {
                    viewModel.topic.collect {
                        binding.topic.setText(it)
                    }
                }
            }
        }

        binding.previousButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.nextButton.setOnClickListener {
            val title = binding.title.text.toString()
            val topic = binding.topic.text.toString()
            viewModel.setTitleValue(title)
            viewModel.setTopicValue(topic)

            findNavController().navigate(
                NewPathTitleAndTopicFragmentDirections.actionTitleAndTopicToDescription())
        }

        return view
    }
}