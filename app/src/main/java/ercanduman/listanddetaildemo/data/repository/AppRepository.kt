package ercanduman.listanddetaildemo.data.repository

import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.util.DataResult

/**
 * Connects to [RestApi] and provides data for ViewModel
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class AppRepository(private val api: RestApi) {

    suspend fun getItems() {
        try {
            val result = api.getItems()
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null && resultBody.isNotEmpty()) {
                    DataResult.Success(resultBody)
                } else {
                    DataResult.Empty
                }
            } else {
                DataResult.Error("Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}")
            }
        } catch (e: Exception) {
            DataResult.Error(e.message ?: "An unknown error occurred...")
        }
    }
}