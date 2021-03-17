package app.login.test.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import app.login.test.ui.login.LoginActivity

/**
 * @author Enlistech.
 */
class SessionManager(private val context: Context) {

    companion object {
        private lateinit var editor: Editor
        private var sharedPreferences: SharedPreferences? = null

        private const val PREF_NAME = "LoginTest"
        private const val IS_LOGIN = "is_login"
        private const val USER_ID = "user_id"
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor.apply()
    }

    var userId: Int?
        get() {
            return sharedPreferences!!.getInt(USER_ID, 0)
        }
        set(userId) {
            editor.putBoolean(IS_LOGIN, true)
            editor.putInt(USER_ID, userId!!)
            editor.commit()
        }

    val isLoggedIn: Boolean
        get() = sharedPreferences!!.getBoolean(IS_LOGIN, false)

    fun logout() {
        editor.clear()
        editor.apply()

        val logoutIntent = Intent(context, LoginActivity::class.java)
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(logoutIntent)
    }
}
