package io.flaterlab.tests.ui.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.ui.test.TestViewModel

class TestingActivity : AppCompatActivity() {

    private lateinit var viewModel: TestingViewModel
    lateinit var userData: UserData

    lateinit var fragments: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)

        val id = intent.getLongExtra("id", 0)

        userData = UserData(this)

        viewModel = ViewModelProvider(this).get(TestingViewModel::class.java)

        viewModel.api = APIManager.create(userData.getToken())

        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.testing_host_fragment, WaitingFragment.newInstance())
        ft.commit()

        if(id > 0){
            viewModel.api.getTestingById(id).observe(this, {
                val fr = TestFragment.newInstance(it.tests[0])
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.testing_host_fragment, fr)
                ft.commit()
            })
        }
    }
}