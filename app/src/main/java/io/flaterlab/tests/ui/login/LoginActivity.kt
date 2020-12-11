package io.flaterlab.tests.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.data.model.LoginData
import io.flaterlab.tests.dialogs.AlertDialog
import io.flaterlab.tests.ui.GreetingActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_button
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.username
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var userData: UserData
    private lateinit var apiManager: APIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        userData = UserData(this)
        apiManager = APIManager.create(null)

        login_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val loginData = LoginData()
            loginData.username = username.text.toString()
            loginData.password = password.text.toString()
            apiManager.login(loginData).observe(this, Observer {
                if(!it.token.isNullOrEmpty()){
                    if(it.error == null){
                        userData.saveToken(it.token!!)
                        progressBar.visibility = View.GONE
                        startActivity(Intent(this, GreetingActivity::class.java))
                        Thread{
                            Thread.sleep(1000)
                            finish()
                        }.start()
                    }else{
                        progressBar.visibility = View.GONE
                        AlertDialog.createAndShow(this, getString(R.string.pass_dnt_match), getString(R.string.paswr_error))
                    }
                }else{
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}