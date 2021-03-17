package app.login.test.ui.home.repostory

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import app.login.test.data.dao.UserDao
import app.login.test.data.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    fun fetchUserById(userId: Int): LiveData<UserEntity> {
        return userDao.fetchUserById(userId)
    }
}
