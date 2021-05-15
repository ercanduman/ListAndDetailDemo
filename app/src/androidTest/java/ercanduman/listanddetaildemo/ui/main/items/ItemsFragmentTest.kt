package ercanduman.listanddetaildemo.ui.main.items

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.model.RestApiResponseItem
import ercanduman.listanddetaildemo.data.model.SalePrice
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.ui.main.MainActivity
import ercanduman.listanddetaildemo.ui.main.MainViewModel
import ercanduman.listanddetaildemo.util.DataResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

/**
 * Contains UI related test cases for [ItemsFragment].
 *
 * If you got an error such as: "Can not perform this action after onSaveInstanceState",
 * Make sure the device you are running the tests on is unlocked. If the screen is off or at the
 * lock screen you will get a stack trace that looks roughly like this
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class ItemsFragmentTest {

    @Test
    fun test_check_if_child_views_displayed() {
        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_Light_DarkActionBar) {
            ItemsFragment()
        }

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar_items)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_error_items)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_click_on_a_item_and_navigate_to_DetailFragment() {
        ActivityScenario.launch(MainActivity::class.java)

        val firstItemPosition = 0

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view_items))
            .perform(actionOnItemAtPosition<ItemsAdapter.ProductHolder>(firstItemPosition, click()))

        onView(withId(R.id.product_image)).check(matches(isDisplayed()))
    }

    @Test
    fun test_click_on_a_item_and_navigate_to_DetailFragment_and_check_items_displayed() {
        ActivityScenario.launch(MainActivity::class.java)

        val firstItemPosition = 0

        onView(withId(R.id.recycler_view_items))
            .perform(actionOnItemAtPosition<ItemsAdapter.ProductHolder>(firstItemPosition, click()))

        onView(withId(R.id.product_image)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
    }

    @Test
    fun test_items_fragment_with_sample_data_from_viewModel() = runBlockingTest {
        val productName = "Default Product Name"
        val product = Product("1", "Desc", "11", productName, SalePrice("1.1", "EUR"), "")
        val responseItem = RestApiResponseItem("description", "123", "Name", listOf(product))

        val apiResponse = RestApiResponse()
        apiResponse.add(responseItem)

        val repository: AppRepository = mockk()
        val mainViewModel = MainViewModel(repository)
        coEvery { repository.getItems() } returns flowOf(DataResult.Success(apiResponse))

        mainViewModel.getItems()
        coVerify { repository.getItems() }

        launchFragmentInContainer(
            themeResId = R.style.Theme_MaterialComponents_Light_DarkActionBar
        ) {
            ItemsFragment().also {
                it.viewModel = mainViewModel
            }
        }

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
    }
}