package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDateBinding

class NewPathDateFragment : Fragment() {
    private var _binding: FragmentNewPathDateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button.setOnClickListener {
            findNavController().navigate(
                NewPathDateFragmentDirections.actionDateToDifficulty())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbar.title = getString(R.string.date_label)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}