package app.login.test.ui.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import app.login.test.data.dao.UserDao
import app.login.test.data.db.AppDatabase
import app.login.test.data.entity.UserEntity
import app.login.test.network.request.LoginRequest
import app.login.test.network.response.RestResponse
import app.login.test.ui.login.repository.LoginRepository
import app.login.test.util.SessionManager
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LoginRepository

    var userDao: UserDao
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val user: MutableLiveData<UserEntity> = MutableLiveData<UserEntity>()
    private val error = MutableLiveData<String>()
    var sessionManager: SessionManager

    init {
        userDao = AppDatabase.getDatabase(application, scope).userDao()
        repository = LoginRepository(userDao)
        sessionManager = SessionManager(application)
    }

    fun login(
        imsi: String,
        imei: String,
        loginRequest: LoginRequest
    ) = scope.launch(Dispatchers.IO) {
        repository.login(imsi, imei, loginRequest)
            .subscribe(object : SingleObserver<RestResponse<UserEntity>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: RestResponse<UserEntity>) {
                    if (response.errorCode == "00") {
                        val userEntity = response.user!!
                        insertUser(userEntity)
                        sessionManager.userId = userEntity.userId
                        user.value = userEntity
                    } else {
                        error.value = response.errorMessage!!
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }
            })
    }

    fun fetchUser(): MutableLiveData<UserEntity> {
        return user
    }

    fun fetchError(): MutableLiveData<String> {
        return error
    }

    fun insertUser(userEntity: UserEntity) = scope.launch(Dispatchers.IO) {
        repository.insertUser(userEntity)
    }
}