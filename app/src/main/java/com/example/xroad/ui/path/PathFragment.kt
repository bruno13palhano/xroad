package com.example.xroad.ui.path

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.google.android.material.appbar.MaterialToolbar

class PathFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_path, container, false)

        val roadId = PathFragmentArgs.fromBundle(requireArguments()).roadId

        val productId = view.findViewById<TextView>(R.id.road_id)
        productId.text = "road id: $roadId"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.title = "Road"

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}