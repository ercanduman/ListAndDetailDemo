package ercanduman.listanddetaildemo.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.util.DataResult
import ercanduman.listanddetaildemo.util.MainDispatcherRule
import ercanduman.listanddetaildemo.util.getOrAwaitValueTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * Contains all unit test cases for [MainViewModel] class.
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class MainViewModelTest {
    /**
     * Swaps the background executor used by the Architecture Components which executes each task synchronously.
     */
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: AppRepository

/*
    @Mock
    lateinit var observer: Observer<DataResult>
*/

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = Mockito.mock(AppRepository::class.java)
        viewModel = MainViewModel()
//        viewModel.dataResult.observeForever(observer)
    }

    @Test
    fun test_call_repository_and_handle_loading_executions() = runBlockingTest {
        val loading = DataResult.Loading
        whenever(repository.getItems()).thenReturn(loading)

        viewModel.getItems()
        val result = viewModel.dataResult.getOrAwaitValueTest()
        assertThat(result).isEqualTo(loading)
    }

/*    @Test
    fun test_call_repository_and_handle_error_execution() = runBlockingTest {
        val error = DataResult.Error("Error")

        whenever(repository.getItems()).thenReturn(DataResult.Loading)
        whenever(repository.getItems()).thenReturn(error)

        verify(observer, times(3)).onChanged(error)

        viewModel.dataResult.observeForever(observer)
        viewModel.getItems()


        val result = viewModel.dataResult.getOrAwaitValueTest()
        assertThat(result).isEqualTo(error)
    }*/
}