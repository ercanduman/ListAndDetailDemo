package ercanduman.listanddetaildemo.ui.main.items

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.databinding.ListItemProductBinding

/**
 * Adapter is a class that adapts every item to a list viewHolder and responsible for providing
 * views that represent items in a data set.
 *
 * ListAdapter is base class of RecyclerView.Adapter for presenting List data in a RecyclerView,
 * including computing diffs between Lists on a background thread. While using a LiveData<List> is
 * an easy way to provide data to the adapter, it isn't required - you can use submitList(List) when new lists are available
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class ItemsAdapter(private val listener: OnProductClickListener) :
    ListAdapter<Product, ItemsAdapter.ProductHolder>(PRODUCT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class ProductHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.apply {
                setOnClickListener {
                    /*
                     * If item clicked during deletion or new insertion processes, then it is possible
                     * that clicked item's position might be invalid which is animating during process.
                     */
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onProductClicked(getItem(adapterPosition))
                    }
                }
            }
        }

        fun bindData(product: Product) {
            binding.apply {
                tvProductName.apply {
                    text = product.name
                    if (product.categoryId == CATEGORY_ID_DRINK) {
                        setBackgroundColor(Color.BLUE)
                    }
                }

                Glide.with(binding.root)
                    .load(RestApi.BASE_URL + product.url)
                    .centerCrop()
                    .error(R.drawable.ic_error_outline)
                    .into(ivProductImage)
            }
        }
    }

    companion object {
        const val CATEGORY_ID_DRINK = "36803"

        /**
         *  DiffUtil can calculate differences between versions of the list.
         */
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }
}