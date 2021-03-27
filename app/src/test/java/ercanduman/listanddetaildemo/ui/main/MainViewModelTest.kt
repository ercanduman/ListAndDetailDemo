package ercanduman.listanddetaildemo.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.model.RestApiResponseItem
import ercanduman.listanddetaildemo.data.model.SalePrice
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.util.DataResult
import ercanduman.listanddetaildemo.util.MainDispatcherRule
import ercanduman.listanddetaildemo.util.getOrAwaitValueTest
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

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
    private lateinit var restApi: RestApi

    @Before
    fun setUp() {
        restApi = mock(RestApi::class.java)
        repository = AppRepository(restApi)
        viewModel = MainViewModel()
        viewModel.setRepository(repository) // Same object should be used.
    }

    @Test
    fun test_call_repository_and_handle_Empty_result() = runBlockingTest {
        val emptyResponse = RestApiResponse()
        val empty: Response<RestApiResponse> = Response.success(emptyResponse)
        whenever(restApi.getItems()).thenReturn(empty)

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest()
        assertThat(result).isEqualTo(DataResult.Empty)
    }

    @Test
    fun test_call_repository_and_check_if_returns_Error_result() = runBlockingTest {
        val message = "No Data Found"
        val error: Response<RestApiResponse> =
            Response.error(500, ResponseBody.create(null, message))
        whenever(restApi.getItems()).thenReturn(error)

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Error
        assertThat(result.message).isEqualTo(AppRepository.generateErrorMessage(error))
    }

    @Test
    fun test_call_repository_for_Success_case() = runBlockingTest {
        val product = Product("1", "Desc", "11", "Name", SalePrice("1.1", "EUR"), "")
        val responseItem = RestApiResponseItem("description", "123", "Name", listOf(product))

        val apiResponse = RestApiResponse()
        apiResponse.add(responseItem)

        val success: Response<RestApiResponse> = Response.success(200, apiResponse)
        whenever(restApi.getItems()).thenReturn(success)

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Success
        assertThat(result.data).isEqualTo(apiResponse)
    }

    @Test
    fun test_call_repository_for_Exception_of_Error_result() = runBlockingTest {
        val errorMessage = "No Data Found"
        whenever(restApi.getItems()).then { throw Exception(errorMessage) }

        viewModel.getItems()

        val result = viewModel.dataResult.getOrAwaitValueTest() as DataResult.Error
        assertThat(result.message).isEqualTo(errorMessage)
    }
}