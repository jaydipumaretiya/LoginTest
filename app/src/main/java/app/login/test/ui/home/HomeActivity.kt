package app.login.test.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.login.test.R
import app.login.test.ui.home.viewmodel.UserViewModel
import app.login.test.ui.login.LoginActivity
import app.login.test.util.SessionManager
import app.login.test.util.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun setContent() {
        val sessionManager = SessionManager(this)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.setUserId(sessionManager.userId!!)
        userViewModel.fetchUserById().observe(this@HomeActivity, {
            tvUsername.text = String.format(getString(R.string.welcome), it.userName)
        })

        btnLogout.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }
    }
}