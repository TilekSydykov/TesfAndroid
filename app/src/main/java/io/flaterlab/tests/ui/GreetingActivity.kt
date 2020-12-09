package io.flaterlab.tests.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.ui.login.LoginActivity
import io.flaterlab.tests.ui.main.MainActivity

class GreetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)
        val data = UserData(this)
        Thread{
            Thread.sleep(1000)
            if(data.getToken().isNullOrEmpty()){

                // TODO: needs to check internet connection and session period

                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                startActivity(Intent(this, MainActivity::class.java))
            }
            Thread.sleep(1000)
            finish()
        }.start()
    }
}