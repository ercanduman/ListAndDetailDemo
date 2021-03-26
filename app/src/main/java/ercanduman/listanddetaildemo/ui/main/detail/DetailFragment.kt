package ercanduman.listanddetaildemo.ui.main.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentDetailBinding.bind(view)
    }
}