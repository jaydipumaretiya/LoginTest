package app.login.test.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import app.login.test.R
import app.login.test.network.request.LoginRequest
import app.login.test.ui.home.HomeActivity
import app.login.test.ui.login.viewmodel.LoginViewModel
import app.login.test.util.SessionManager
import app.login.test.util.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this@LoginActivity)

        if (sessionManager.isLoggedIn) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        } else {
            setContentView(R.layout.activity_login)
        }
    }

    override fun setContent() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        edtUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length < 30 || s.length > 4) {
                    tilUserName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length < 16 || s.length > 4) {
                    tilUserName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        btnLogin.setOnClickListener {
            if (isNetworkConnected)
                validate()
        }
    }

    private fun validate() {
        val username = edtUserName.text.toString()
        val password = edtPassword.text.toString()
        tilUserName.error = null
        tilPassword.error = null
        when {
            username.isEmpty() -> {
                tilUserName.error = "Please enter username"
                return
            }
            username.length > 30 || username.length < 4 -> {
                tilUserName.error = "Please enter valid username"
                return
            }
            password.isEmpty() -> {
                tilPassword.error = "Please enter password"
                return
            }
            password.length > 16 || password.length < 4 -> {
                tilPassword.error = "Please enter valid password"
                return
            }
            else -> {
                val request = LoginRequest()
                request.username = username
                request.password = password
                Log.e("imsi number ", "" + fetchIMSINumber())
                Log.e("imei number ", "" + fetchIMEINumber())

                requestLogin(request)
            }
        }
    }

    private fun fetchIMSINumber(): String {

        return "357175048449937"
    }

    private fun fetchIMEINumber(): String {

        return "510110406068589"
    }

    private fun requestLogin(request: LoginRequest) {
        showLoading()
        loginViewModel.login(fetchIMSINumber(), fetchIMEINumber(), request)
        loginViewModel.fetchUser().observe(this, {
            hideLoading()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        })
        loginViewModel.fetchError().observe(this, {
            hideLoading()
            showToast(it)
        })

//        val apiInterface = APIClient.client!!.create(APIInterface::class.java)
//        apiInterface.login(fetchIMSINumber(), fetchIMEINumber(), request)
//            .subscribe(object : SingleObserver<RestResponse<UserEntity>> {
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onSuccess(response: RestResponse<UserEntity>) {
//                    if (response.errorCode == "00") {
//
//                    } else {
//
//                    }
//                }
//
//                override fun onError(e: Throwable) {
//                }
//            })
    }
}