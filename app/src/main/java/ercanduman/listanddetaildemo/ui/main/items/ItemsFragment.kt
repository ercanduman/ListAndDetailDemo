package ercanduman.listanddetaildemo.ui.main.items

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.databinding.FragmentItemsBinding
import ercanduman.listanddetaildemo.ui.main.MainViewModel
import ercanduman.listanddetaildemo.util.DataResult

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemsFragment : Fragment(R.layout.fragment_items) {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentItemsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getItems()
        handleData()
    }

    private fun handleData() {
        viewModel.dataResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataResult.Empty -> showContent(message = getString(R.string.no_data_found))
                is DataResult.Error -> showContent(message = result.message)
                is DataResult.Loading -> binding.progressBar.isVisible = true
                is DataResult.Success -> showContent(true)
            }
        }
    }

    private fun showContent(contentAvailable: Boolean = false, message: String = "") {
        binding.apply {
            progressBar.isVisible = false
            recyclerView.isVisible = contentAvailable
            tvError.isVisible = contentAvailable.not()
            tvError.text = message
        }
    }
}