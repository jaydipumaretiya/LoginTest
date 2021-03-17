package app.login.test.network

import app.login.test.data.entity.UserEntity
import app.login.test.network.request.LoginRequest
import app.login.test.network.response.RestResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {

    @POST("login")
    fun login(
        @Header("IMSI") imsiNumber: String,
        @Header("IMEI") imeiNumber: String,
        @Body loginRequest: LoginRequest
    ): Single<RestResponse<UserEntity>>
}