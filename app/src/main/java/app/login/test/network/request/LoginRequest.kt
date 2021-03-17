package app.login.test.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {
    @Expose
    @SerializedName("username")
    var username: String? = null

    @Expose
    @SerializedName("password")
    var password: String? = null
}