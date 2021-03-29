package ercanduman.listanddetaildemo.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.SalePrice
import org.junit.Test

/**
 * Contains Instrumentation test cases for [DetailFragment].
 *
 * @author ercanduman
 * @since 28.03.2021
 */
class DetailFragmentTest {

    @Test
    fun test_launch_details_fragment() {
        val product = Product("1", "Desc", "11", "name", SalePrice("1.1", "EUR"), "")

        val bundle = Bundle()
        bundle.putParcelable("product", product)
        launchFragmentInContainer<DetailFragment>(fragmentArgs = bundle)
    }

    @Test
    fun test_launch_detail_fragment_and_check_child_views_visibility() {
        val product = Product("1", "Desc", "11", "name", SalePrice("1.1", "EUR"), "")

        val bundle = Bundle()
        bundle.putParcelable("product", product)
        launchFragmentInContainer<DetailFragment>(fragmentArgs = bundle)

        onView(withId(R.id.product_image)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.product_name)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.product_description)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.product_price)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_launch_detail_fragment_and_check_if_passed_item_fields_displayed() {
        val productName = "Default Product Name"
        val productDesc = "Default Description"
        val product =
            Product("1", description = productDesc, "11", productName, SalePrice("1.1", "EUR"), "")

        val bundle = Bundle()
        bundle.putParcelable("product", product)
        launchFragmentInContainer<DetailFragment>(fragmentArgs = bundle)

        onView(withId(R.id.product_name)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withText(productName)).check(matches(isDisplayed()))

        onView(withId(R.id.product_description)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withText(productDesc)).check(matches(isDisplayed()))
    }

    @Test
    fun test_launch_detail_fragment_and_check_if_field_display_correct_text() {
        val productName = "Default Product Name"
        val productDesc = "Default Description"
        val product =
            Product("1", description = productDesc, "11", productName, SalePrice("1.1", "EUR"), "")

        val bundle = Bundle()
        bundle.putParcelable("product", product)
        launchFragmentInContainer<DetailFragment>(fragmentArgs = bundle)

        onView(withId(R.id.product_name)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.product_description)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.product_name)).check(matches(withText(productName)))
        onView(withId(R.id.product_description)).check(matches(withText(productDesc)))
    }
}