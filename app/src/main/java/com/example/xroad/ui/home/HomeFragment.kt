package com.example.xroad.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.google.android.material.appbar.MaterialToolbar

class HomeFragment : Fragment() {
    private lateinit var drawer: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        drawer = ((activity as MainActivity)).findViewById(R.id.drawer_layout)

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToRoad(1L)
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24)
        toolbar.title = "Home"

        toolbar.setNavigationOnClickListener {
            drawer.open()
        }
    }
}