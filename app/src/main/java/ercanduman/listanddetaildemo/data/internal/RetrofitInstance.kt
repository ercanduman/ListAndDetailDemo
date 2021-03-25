package ercanduman.listanddetaildemo.data.internal

import ercanduman.listanddetaildemo.data.network.RestApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Responsible for creating [RestApi] instance with singleton pattern.
 *
 * @author ercanduman
 * @since  25.03.2021
 */
object RetrofitInstance {

    val restApi: RestApi by lazy {
        Retrofit.Builder()
            .baseUrl(RestApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestApi::class.java)
    }
}