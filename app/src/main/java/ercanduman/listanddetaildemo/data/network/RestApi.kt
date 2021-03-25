package ercanduman.listanddetaildemo.data.network

import ercanduman.listanddetaildemo.data.model.RestApiResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Connects to URL and retrieves data from web server.
 *
 * @author ercanduman
 * @since  25.03.2021
 */
interface RestApi {

    companion object {
        const val BASE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com/"
    }

    @GET("/")
    suspend fun getItems(): Response<RestApiResponse>

}