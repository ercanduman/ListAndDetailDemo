package ercanduman.listanddetaildemo.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.model.RestApiResponseItem
import ercanduman.listanddetaildemo.data.model.SalePrice
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.util.DataResult
import ercanduman.listanddetaildemo.util.MainDispatcherRule
import ercanduman.listanddetaildemo.util.getOrAwaitValueTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
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

    @Before
    fun setUp() {
        repository = mock(AppRepository::class.java)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun test_call_repository_and_handle_Empty_result() = runBlockingTest {
        whenever(repository.getItems()).thenReturn(flowOf(DataResult.Empty))

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest()
        assertThat(result).isEqualTo(DataResult.Empty)
    }

    @Test
    fun test_call_repository_and_check_if_returns_Error_result() = runBlockingTest {
        val message = "No Data Found"
        whenever(repository.getItems()).thenReturn(flowOf(DataResult.Error(message)))

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Error
        assertThat(result.message).isEqualTo(message)
    }

    @Test
    fun test_call_repository_for_Success_case() = runBlockingTest {
        val product = Product("1", "Desc", "11", "Name", SalePrice("1.1", "EUR"), "")
        val responseItem = RestApiResponseItem("description", "123", "Name", listOf(product))

        val apiResponse = RestApiResponse()
        apiResponse.add(responseItem)

        whenever(repository.getItems()).thenReturn(flowOf(DataResult.Success(apiResponse)))

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Success
        assertThat(result.data).isEqualTo(apiResponse)
    }

    @Test
    fun test_call_repository_for_Exception_of_Error_result() = runBlockingTest {
        val errorMessage = "No Data Found"
        whenever(repository.getItems()).thenReturn(flowOf(DataResult.Error(errorMessage)))

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Error
        assertThat(result.message).isEqualTo(errorMessage)
    }
}