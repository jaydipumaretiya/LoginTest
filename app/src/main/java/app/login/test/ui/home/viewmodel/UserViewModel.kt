package app.login.test.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import app.login.test.data.db.AppDatabase
import app.login.test.ui.home.repostory.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: UserRepository
    private val userId: MutableLiveData<Int> = MutableLiveData()

    init {
        val userDao = AppDatabase.getDatabase(application, scope).userDao()
        repository = UserRepository(userDao)
    }

    fun setUserId(id: Int) {
        userId.value = id
    }

    fun fetchUserById() = repository.fetchUserById(userId.value!!)

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}