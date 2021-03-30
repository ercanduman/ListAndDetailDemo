package ercanduman.listanddetaildemo.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        val product: Product = args.product

        binding.apply {
            productName.setContent(product.name)

            val priceText = "${product.salePrice.currency} ${product.salePrice.amount}"
            productPrice.setContent(priceText)

            Glide.with(this@DetailFragment)
                .load(RestApi.BASE_URL + product.url)
                .centerCrop()
                .error(R.drawable.ic_error_outline)
                .into(productImage)
        }
    }

    private fun TextView.setContent(content: String?) {
        this.isVisible = content != null && content.isNotEmpty()
        text = content
    }
}