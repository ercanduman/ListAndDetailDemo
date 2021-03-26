package ercanduman.listanddetaildemo.ui.main.items

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import ercanduman.listanddetaildemo.R
import ercanduman.listanddetaildemo.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Contains UI related test cases for [ItemsFragment].
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class ItemsFragmentTest {

    /**
     * Creating a rule for activity scenario will run Before() methods, then the Test method, and finally any After() methods.
     *  This way no need to use below code for all test cases:
     *      ActivityScenario.launch(MainActivity::class.java)
     */
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_fragment_launched_and_views_displayed() {
        onView(withText(R.string.items)).check(matches(isDisplayed()))
    }

    @Test
    fun test_check_if_recycler_view_displayed() {
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