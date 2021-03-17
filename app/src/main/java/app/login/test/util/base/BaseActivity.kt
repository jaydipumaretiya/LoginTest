package app.login.test.util.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.login.test.R
import app.login.test.util.NetworkUtils

abstract class BaseActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    private val isActionBarOverlay: Boolean
        get() = false

    val isNetworkConnected: Boolean
        get() {
            return if (!NetworkUtils.isNetworkConnected(this@BaseActivity)) {
                showToast(getString(R.string.error_internet))
                false
            } else {
                true
            }
        }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setContent()
    }

    protected abstract fun setContent()

    fun showToast(successMsg: String) {
        Toast.makeText(this@BaseActivity, successMsg, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        dialog = Dialog(this@BaseActivity)
        val inflate = LayoutInflater.from(this@BaseActivity).inflate(R.layout.dialog_progress, null)
        dialog!!.setContentView(inflate)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }

    fun hideLoading() {
        if (dialog!!.isShowing) {
            dialog!!.cancel()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
