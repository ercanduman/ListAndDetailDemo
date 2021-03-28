package ercanduman.listanddetaildemo.ui.main.items

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.model.RestApiResponseItem
import ercanduman.listanddetaildemo.data.model.SalePrice
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.ui.main.MainViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

/**
 * Contains UI related test cases for [ItemsFragment].
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class ItemsFragmentTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: AppRepository
    private lateinit var restApi: RestApi

    @Before
    fun setUp() {
        restApi = Mockito.mock(RestApi::class.java)
        repository = AppRepository(restApi)
        viewModel = MainViewModel()
        viewModel.setRepository(repository) // Same object should be used.
    }

    @Test
    fun test_check_if_child_views_displayed() {
        launchFragmentInContainer<ItemsFragment>()

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar_items)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_error_items)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_with_data() = runBlockingTest {
        launchFragmentInContainer<ItemsFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_DarkActionBar
        )

        val product = Product("1", "Desc", "11", "Name", SalePrice("1.1", "EUR"), "")
        val responseItem = RestApiResponseItem("description", "123", "Name", listOf(product))

        val apiResponse = RestApiResponse()
        apiResponse.add(responseItem)

        val success: Response<RestApiResponse> = Response.success(200, apiResponse)
        Mockito.`when`(restApi.getItems()).thenReturn(success)

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
    }

    @Test
    fun test_click_on_a_item_and_navigate_to_DetailFragment() {
        val firstItemPosition = 0

        onView(withId(R.id.recycler_view_items))
            .perform(actionOnItemAtPosition<ItemsAdapter.ProductHolder>(firstItemPosition, click()))

        onView(withId(R.id.product_image)).check(matches(isDisplayed()))
    }

    @Test
    fun test_click_on_a_item_and_navigate_to_DetailFragment_and_check_items_displayed() {
        val firstItemPosition = 0

        onView(withId(R.id.recycler_view_items))
            .perform(actionOnItemAtPosition<ItemsAdapter.ProductHolder>(firstItemPosition, click()))

        onView(withId(R.id.product_image)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.recycler_view_items)).check(matches(isDisplayed()))
    }
}