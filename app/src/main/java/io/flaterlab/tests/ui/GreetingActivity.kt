package io.flaterlab.tests.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.ui.login.LoginActivity
import io.flaterlab.tests.ui.main.MainActivity
import io.flaterlab.tests.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_greeting.*

class GreetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)
        val data = UserData(this)
        Thread{
            Thread.sleep(1000)
            if(data.getToken().isNullOrEmpty()){
                signup.visibility = View.VISIBLE
                login.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                loading_text.visibility = View.GONE
            }else{
                startActivity(Intent(this, ApgradeMainActivity::class.java))
                Thread.sleep(1000)
                finish()
            }
        }.start()

        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishWithDelay()
        }
        signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finishWithDelay()
        }
    }

    private fun finishWithDelay(){
        Thread{
            Thread.sleep(1000)
            finish()
        }.start()
    }
}