package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathTitleAndTopicBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        binding.button.setOnClickListener {
            val title = binding.title.text.toString()
            val topic = binding.topic.text.toString()
            viewModel.setTitleValue(title)
            viewModel.setTopicValue(topic)

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