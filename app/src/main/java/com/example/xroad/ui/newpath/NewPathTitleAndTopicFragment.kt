package com.example.xroad.ui.newpath

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
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

        binding.title.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val title = binding.title.text.toString()
                viewModel.setTitleValue(title)
            }
        }

        binding.topic.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val topic = binding.topic.text.toString()
                viewModel.setTopicValue(topic)
            }
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(
                NewPathTitleAndTopicFragmentDirections.actionTitleAndTopicToDescription())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.new_path_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.restore_values -> {
                        restoreTitleAndTopicValues()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun restoreTitleAndTopicValues() {
        binding.title.setText("")
        binding.topic.setText("")
        viewModel.restoreTitleAndTopicValue()
    }
}