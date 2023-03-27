package com.example.xroad.ui.newpath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return view
    }
}