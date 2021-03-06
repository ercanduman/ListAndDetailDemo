package ercanduman.listanddetaildemo.data.repository

import ercanduman.listanddetaildemo.data.model.RestApiResponse
import ercanduman.listanddetaildemo.data.network.RestApi
import ercanduman.listanddetaildemo.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * Connects to [RestApi] and provides data for ViewModel
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class AppRepository(private val api: RestApi) {

    suspend fun getItems(): Flow<DataResult> = flow {
        emit(DataResult.Loading)
        try {
            val result = api.getItems()
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null && resultBody.isNotEmpty()) {
                    emit(DataResult.Success(resultBody))
                } else {
                    emit(DataResult.Empty)
                }
            } else {
                emit(DataResult.Error(generateErrorMessage(result)))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message ?: "An unknown error occurred..."))
        }
    }

    companion object {
        fun generateErrorMessage(result: Response<RestApiResponse>) =
            "Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}"
    }
}