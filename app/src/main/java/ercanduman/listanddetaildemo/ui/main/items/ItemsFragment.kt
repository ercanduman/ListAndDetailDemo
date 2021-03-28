package ercanduman.listanddetaildemo.ui.main.items

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.databinding.FragmentItemsBinding
import ercanduman.listanddetaildemo.ui.main.MainViewModel
import ercanduman.listanddetaildemo.util.DataResult

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemsFragment : Fragment(R.layout.fragment_items), ItemsAdapter.OnProductClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentItemsBinding

    private val productAdapter = ItemsAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsBinding.bind(view)

        viewModel.getItems()
        getData()
    }

    private fun getData() {
        viewModel.dataResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataResult.Empty -> displayItems(message = getString(R.string.no_data_found))
                is DataResult.Error -> displayItems(message = result.message)
                is DataResult.Loading -> binding.progressBarItems.isVisible = true
                is DataResult.Success -> handleData(result.data)
            }
        }
    }

    private fun displayItems(message: String = "") {
        binding.apply {
            progressBarItems.isVisible = false
            recyclerViewItems.isVisible = message.isEmpty()
            tvErrorItems.isVisible = message.isNotEmpty()
            tvErrorItems.text = message
        }
    }

    private fun handleData(data: RestApiResponse) {
        displayItems()
        val mutableList = mutableListOf<Product>()
        data.forEach { item -> mutableList.addAll(item.products) }
        productAdapter.submitList(mutableList)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewItems.apply {
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    override fun onProductClicked(product: Product) {
        Toast.makeText(requireContext(), "${product.name} clicked.", Toast.LENGTH_SHORT).show()
        val action = ItemsFragmentDirections.actionItemFragmentToDetailFragment(product)
        findNavController().navigate(action)
    }
}