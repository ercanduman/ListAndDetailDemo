package ercanduman.listanddetaildemo.data.repository

import com.google.common.truth.Truth.assertThat
import ercanduman.listanddetaildemo.data.model.Product
import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.model.RestApiResponseItem
import ercanduman.listanddetaildemo.data.model.SalePrice
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.util.DataResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response


/**
 * Stored [AppRepository] relates unit test cases.
 *
 * @author ercanduman
 * @since 26.03.2021
 */
class AppRepositoryTest {

    private lateinit var repository: AppRepository
    private lateinit var restApi: RestApi

    @Before
    fun setUp() {
        restApi = mockk()
        repository = AppRepository(restApi)
    }

    @Test
    fun test_call_API_forErrorRetrofitResponse() = runBlockingTest {
        val message = "No Data Found"
        val error: Response<RestApiResponse> =
            Response.error(500, ResponseBody.create(null, message))
        coEvery { restApi.getItems() } returns error

        // To collect second emitted flow value, toList()[1] should be used.
        val dataResult: DataResult = repository.getItems().toList()[1]
        val result = AppRepository.generateErrorMessage(error)
        assertThat((dataResult as DataResult.Error).message).isEqualTo(result)
    }

    @Test
    fun test_call_API_forUnknownException() = runBlockingTest {
        val errorMessage = "An unknown error occurred..."
        coEvery { restApi.getItems() } throws RuntimeException(errorMessage)

        val dataResult: DataResult = repository.getItems().toList()[1]
        assertThat((dataResult as DataResult.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun test_call_API_forEmptyRetrofitResponse() = runBlockingTest {
        val emptyResponse = RestApiResponse()
        val empty: Response<RestApiResponse> = Response.success(emptyResponse)
        coEvery { restApi.getItems() } returns empty

        val dataResult: DataResult = repository.getItems().toList()[1]
        assertThat(dataResult).isEqualTo(DataResult.Empty)
    }

    @Test
    fun test_call_API_forSuccessRetrofitResponse() = runBlockingTest {
        val product = Product("1", "Desc", "11", "Name", SalePrice("1.1", "EUR"), "")
        val responseItem = RestApiResponseItem("description", "123", "Name", listOf(product))
        val apiResponse = RestApiResponse()
        apiResponse.add(responseItem)

        val success: Response<RestApiResponse> = Response.success(200, apiResponse)
        coEvery { restApi.getItems() } returns success

        val dataResult: DataResult = repository.getItems().toList()[1]
        assertThat((dataResult as DataResult.Success).data).isEqualTo(apiResponse)
    }
}