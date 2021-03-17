package app.login.test.ui.login.repository

import androidx.annotation.WorkerThread
import app.login.test.data.dao.UserDao
import app.login.test.data.entity.UserEntity
import app.login.test.network.APIClient
import app.login.test.network.APIInterface
import app.login.test.network.request.LoginRequest
import app.login.test.network.response.RestResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository(private val userDao: UserDao) {

    private var apiInterface: APIInterface? = null

    init {
        apiInterface = APIClient.client!!.create(APIInterface::class.java)
    }

    fun login(
        imsi: String,
        imei: String,
        loginRequest: LoginRequest
    ): Single<RestResponse<UserEntity>> {
        return apiInterface!!.login(imsi, imei, loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @WorkerThread
    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }
}