package app.login.test.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_master")
class UserEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var userId: Int = 0

    @ColumnInfo(name = "userName")
    var userName: String? = null
}