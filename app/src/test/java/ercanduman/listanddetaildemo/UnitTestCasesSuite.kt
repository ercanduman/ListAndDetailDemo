package ercanduman.listanddetaildemo

import ercanduman.listanddetaildemo.data.repository.AppRepositoryTest
import ercanduman.listanddetaildemo.ui.main.MainViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite to run all local unit test classes with a single click.
 *
 * Unit tests which don't need any Android device to execute on.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * @author ercanduman
 * @since  28.03.2021
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(AppRepositoryTest::class, MainViewModelTest::class)
class UnitTestCasesSuite