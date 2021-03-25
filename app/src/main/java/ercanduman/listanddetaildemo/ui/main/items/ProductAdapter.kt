package ercanduman.listanddetaildemo.ui.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.databinding.ListItemProductBinding

/**
 * Adapter is a class that adapts every item to a list viewHolder and responsible for providing
 * views that represent items in a data set.
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bindData(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ProductHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Product) {
            binding.apply {
                tvArticleTitle.text = product.name
            }
        }
    }
}