package ercanduman.listanddetaildemo

import ercanduman.listanddetaildemo.ui.main.MainActivityTest
import ercanduman.listanddetaildemo.ui.main.detail.DetailFragmentTest
import ercanduman.listanddetaildemo.ui.main.items.ItemsFragmentTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite to run all instrumented test classes with a single click.
 *
 * Instrumented test, which will execute on an Android device.
 *
 * Notice: If you got an error such as: "Can not perform this action after onSaveInstanceState",
 * Make sure the device you are running the tests on is unlocked. If the screen is off or at the
 * lock screen you will get a stack trace that looks roughly like this
 *
 * @author ercanduman
 * @since  26.03.2021
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(MainActivityTest::class, ItemsFragmentTest::class, DetailFragmentTest::class)
class InstrumentedTestCasesSuite