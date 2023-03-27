package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDifficultyBinding

class NewPathDifficultyFragment : Fragment() {
    private var _binding: FragmentNewPathDifficultyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDifficultyBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button.setOnClickListener {
            findNavController().navigate(
                NewPathDifficultyFragmentDirections.actionDifficultyToTitleAndTopic())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbar.title = getString(R.string.difficulty_label)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}