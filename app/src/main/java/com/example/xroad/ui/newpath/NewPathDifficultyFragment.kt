package com.example.xroad.ui.newpath

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.model.model.Difficulty
import com.example.model.model.Path
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDifficultyBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPathDifficultyFragment : Fragment() {
    private var _binding: FragmentNewPathDifficultyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPathViewModel by activityViewModels()

    private var title = ""
    private var topic = ""
    private var description = ""
    private var duration = 0L
    private var date = 0L
    private var difficulty = Difficulty.NORMAL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDifficultyBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.lifecycleOwner = viewLifecycleOwner
        binding.uiEvents = this
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.title.collect {
                    title = it
                }
            }
            launch {
                viewModel.topic.collect {
                    topic = it
                }
            }
            launch {
                viewModel.description.collect {
                    description = it
                }
            }
            launch {
                viewModel.duration.collect {
                    duration = it
                }
            }
            launch {
                viewModel.date.collect {
                    date = it
                }
            }
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
                        viewModel.restoreDifficultyValue()
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

    fun insertPath() {
        navigateToTitleAndTopic()

        viewLifecycleOwner.lifecycleScope.launch {
                viewModel.insertPath(
                    Path(
                        id = 0L,
                        title = title,
                        topic = topic,
                        description = description,
                        durationInMilliseconds = duration,
                        dateInMilliseconds = date,
                        difficulty = difficulty
                    )
                )
                viewModel.restorePathValues()
            }
    }

    private fun navigateToTitleAndTopic() {
        findNavController().navigate(
                NewPathDifficultyFragmentDirections.actionDifficultyToTitleAndTopic())
    }
}