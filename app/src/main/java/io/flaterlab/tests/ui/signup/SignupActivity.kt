package io.flaterlab.tests.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.data.model.SignUpData
import io.flaterlab.tests.dialogs.AlertDialog
import io.flaterlab.tests.ui.GreetingActivity

import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var userData: UserData
    private lateinit var apiManager: APIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userData = UserData(this)
        apiManager = APIManager.create(null)

        login_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val signUpData = SignUpData()
            signUpData.email = email.text.toString()
            signUpData.username = username.text.toString()
            signUpData.password = password.text.toString()
            signUpData.password2 = password2.text.toString()

            apiManager.signup(signUpData).observe(this, Observer {

                if(it != null){
                    if(it.error == null){
                        userData.saveToken(it.token!!)
                        progressBar.visibility = View.GONE
                        startActivity(Intent(this, GreetingActivity::class.java))
                        Thread{
                            Thread.sleep(1000)
                            finish()
                        }.start()
                    }else{
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