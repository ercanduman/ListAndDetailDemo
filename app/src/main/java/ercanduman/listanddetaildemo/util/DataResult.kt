package ercanduman.listanddetaildemo.util

import ercanduman.listanddetaildemo.data.model.RestApiResponse

/**
 * Handles api data and based on api executions returns classes such as Success, Error,
 * Loading, Empty.
 *
 * @author ercanduman
 * @since  25.03.2021
 */
sealed class DataResult {
    class Success(val data: RestApiResponse) : DataResult()
    class Error(val message: String) : DataResult()
    object Empty : DataResult()
    object Loading : DataResult()
}