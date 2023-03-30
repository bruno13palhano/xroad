package com.example.xroad.ui.newpath

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.RadioGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDifficultyBinding.inflate(inflater, container, false)
        val view = binding.root

        var title = ""
        var topic = ""
        var description = ""
        var duration = 0L
        var date = 0L
        var difficulty = Difficulty.NORMAL

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
            launch {
                viewModel.difficulty.collect {
                    difficulty = it
                    checkDifficultyRadioButton(it)
                }
            }
        }

        binding.difficultyGroup.setOnCheckedChangeListener { radioGroup, i ->
           setDifficulty(radioGroup)
        }

        binding.previousButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.doneButton.setOnClickListener {
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

            findNavController().navigate(
                NewPathDifficultyFragmentDirections.actionDifficultyToTitleAndTopic())
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
                        println("Difficulty")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun checkDifficultyRadioButton(difficulty: Difficulty) {
        when (difficulty) {
            Difficulty.VERY_EASY -> {
                binding.veryEasyDifficulty.isChecked = true
            }
            Difficulty.EASY -> {
                binding.easyDifficulty.isChecked = true
            }
            Difficulty.NORMAL -> {
                binding.normalDifficulty.isChecked = true
            }
            Difficulty.HARD -> {
                binding.hardDifficulty.isChecked = true
            }
            Difficulty.VERY_HARD -> {
                binding.veryHardDifficulty.isChecked = true
            }
        }
    }

    private fun setDifficulty(radioGroup: RadioGroup) {
        when (radioGroup.checkedRadioButtonId) {
            binding.veryEasyDifficulty.id -> {
                viewModel.setDifficulty(Difficulty.VERY_EASY)
            }
            binding.easyDifficulty.id -> {
                viewModel.setDifficulty(Difficulty.EASY)
            }
            binding.normalDifficulty.id -> {
                viewModel.setDifficulty(Difficulty.NORMAL)
            }
            binding.hardDifficulty.id -> {
                viewModel.setDifficulty(Difficulty.HARD)
            }
            binding.veryHardDifficulty.id -> {
                viewModel.setDifficulty(Difficulty.VERY_HARD)
            }
        }
    }
}