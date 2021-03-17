package app.login.test.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.login.test.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("SELECT id FROM user_master WHERE id=:userId")
    fun fetchUserById(userId: Int): LiveData<UserEntity>
}