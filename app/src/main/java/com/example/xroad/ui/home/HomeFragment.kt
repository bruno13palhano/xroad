package com.example.xroad.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.example.xroad.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        drawer = ((activity as MainActivity)).findViewById(R.id.drawer_layout)

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToNewPath()
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_menu_24)
        binding.toolbar.title = getString(R.string.app_name)

        binding.toolbar.setNavigationOnClickListener {
            drawer.open()
        }
    }
}