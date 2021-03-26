package ercanduman.listanddetaildemo.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import ercanduman.listanddetaildemo.R
import org.junit.Rule
import org.junit.Test

/**
 * Contains UI related test cases for [MainActivity].
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class MainActivityTest {

    /**
     * Creating a rule for activity scenario will run Before() methods, then the Test method, and finally any After() methods.
     *  This way no need to use below code for all test cases:
     *      ActivityScenario.launch(MainActivity::class.java)
     */
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_activity_launched() {
    }

    @Test
    fun test_launch_activity_and_check_toolbar_displayed() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_launch_activity_and_check_items_fragment_title_displayed() {
        onView(withText(R.string.items)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}