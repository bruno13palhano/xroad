package com.example.xroad.ui.newpath

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDescriptionBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPathDescriptionFragment : Fragment() {
    private var _binding: FragmentNewPathDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPathViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_new_path_description, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.title = getString(R.string.description_label)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.uiEvents = this

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
                        viewModel.restoreDescriptionValue()
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

    fun navigateToDuration() {
        findNavController().navigate(
            NewPathDescriptionFragmentDirections.actionDescriptionToDuration())
    }
}