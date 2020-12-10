package io.flaterlab.tests.ui.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.flaterlab.tests.R
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.ui.test.attempt.TestAttemptFragment
import io.flaterlab.tests.ui.test.attempt.TestFinishedInterface

class TestActivity : AppCompatActivity() {

    private lateinit var viewModel: TestViewModel
    lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        val id = intent.getLongExtra("id", 0)

        userData = UserData(this)
        val token = userData.getToken()

        viewModel.initApi(token)

        if(id > 0){
            val  t = userData.getTest(id)
            if(t == null){
                viewModel.getTest(id).observe(this, {
                    it?.let {
                        val ft = supportFragmentManager.beginTransaction()
                        ft.replace(R.id.test_fragment, getFragment(it))
                        ft.commit()
                    }
                })
            }else{
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.test_fragment, getFragment(t))
                ft.commit()
            }
        }
    }

    private fun getFragment(test: Test): Fragment {

        val attempts:ArrayList<Attempt> = userData.getAttempts(test.id)

        val beginTestButton = View.OnClickListener {
            val a = Attempt.begin(test)
            a.testId = test.id
            attempts.add(a)
            userData.saveAttempts(test.id, attempts)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.test_fragment, getFragment(test))
            ft.commit()
            userData.saveTest(test)
        }

        var fragment: Fragment = TestFragment().apply {
            this.test = test
            this.attempts = attempts
            beginListener = beginTestButton
        }

        attempts.forEachIndexed {index, it ->
            if(!it.isEnded){
                fragment = TestAttemptFragment().apply {
                    this.test = test
                    attempt = it
                    attemptIndex = index
                    testFinishedInterface = object : TestFinishedInterface {
                        override fun finish() {
                            userData.deleteTest(test.id)
                            val ft = supportFragmentManager.beginTransaction()
                            ft.replace(R.id.test_fragment, getFragment(test))
                            ft.commit()
                        }
                    }
                }
                return@forEachIndexed
            }
        }

        return fragment
    }
}