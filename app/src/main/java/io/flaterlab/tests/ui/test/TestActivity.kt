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
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

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
            progressBar2.visibility = View.VISIBLE
            viewModel.api.beginAttempt(test.id).observe(this, {
                progressBar2.visibility = View.GONE
                val a = Attempt.begin(test)
                a.testId = test.id
                a.startTime = Date()
                it?.let {
                    a.id = it.id
                    a.startTime = it.startTime
                }
                attempts.add(a)
                userData.saveAttempts(test.id, attempts)
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.test_fragment, getFragment(test))
                ft.commit()
                userData.saveTest(test)
            })
        }

        var fragment: Fragment = TestFragment().apply {
            this.test = test
            this.attempts = attempts
            beginListener = beginTestButton
        }

        attempts.forEachIndexed {index, it: Attempt ->
            if(!it.isEnded){
                fragment = TestAttemptFragment().apply {
                    this.test = test
                    attempt = it
                    attemptIndex = index
                    testFinishedInterface = object : TestFinishedInterface {
                        override fun finish(attempt: Attempt) {
                            viewModel.api.finishAttempt(it.id).observe(this@TestActivity, {
                                userData.replaceAttemptByIndex(test.id, index, attempt)
                                userData.deleteTest(test.id)
                                val ft = supportFragmentManager.beginTransaction()
                                ft.replace(R.id.test_fragment, getFragment(test))
                                ft.commit()
                            })
                        }
                    }
                }
                return@forEachIndexed
            }
        }
        return fragment
    }

}