package ercanduman.listanddetaildemo.ui.main.items

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.internal.RetrofitInstance
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.databinding.FragmentItemsBinding
import ercanduman.listanddetaildemo.ui.main.MainViewModel
import ercanduman.listanddetaildemo.ui.main.MainViewModelFactory
import ercanduman.listanddetaildemo.util.DataResult

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemsFragment : Fragment(R.layout.fragment_items), ItemsAdapter.OnProductClickListener {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentItemsBinding

    private val productAdapter = ItemsAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsBinding.bind(view)

        initViewModel()
        viewModel.getItems()
        getData()
    }

    private fun initViewModel() {
        val repository = AppRepository(RetrofitInstance.restApi)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
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
        val items = data.flatMap { category -> category.products }
        productAdapter.submitList(items)
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