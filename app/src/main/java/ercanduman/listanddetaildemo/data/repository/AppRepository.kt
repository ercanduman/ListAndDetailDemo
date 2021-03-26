package ercanduman.listanddetaildemo.data.repository

import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.util.DataResult
import retrofit2.Response

/**
 * Connects to [RestApi] and provides data for ViewModel
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class AppRepository(private val api: RestApi) {

    suspend fun getItems(): DataResult = try {
        val result = api.getItems()
        if (result.isSuccessful) {
            val resultBody = result.body()
            if (resultBody != null && resultBody.isNotEmpty()) {
                DataResult.Success(resultBody)
            } else {
                DataResult.Empty
            }
        } else {
            DataResult.Error(generateErrorMessage(result))
        }
    } catch (e: Exception) {
        DataResult.Error(e.message ?: "An unknown error occurred...")
    }

    companion object {
        fun generateErrorMessage(result: Response<RestApiResponse>) =
            "Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}"
    }
}