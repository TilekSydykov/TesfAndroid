package io.flaterlab.tests.ui.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import io.flaterlab.tests.R
import io.flaterlab.tests.data.TestData
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.data.model.local.AnswerSheet
import io.flaterlab.tests.data.model.local.TestingAttempt
import io.flaterlab.tests.dialogs.AlertDialog

class TestingActivity : AppCompatActivity() {

    enum class FragmentType{
        TEST,
        REST,
        RESULT
    }

    private lateinit var viewModel: TestingViewModel
    private lateinit var userData: UserData
    private lateinit var testData: TestData

    private var currentTestIndex: Int = 0
    private lateinit var ft: FragmentTransaction
    private var testingAttempt: TestingAttempt = TestingAttempt()
    private var lastFragmentType = FragmentType.REST
    private var tests = arrayListOf<Test>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)

        val id = intent.getLongExtra("id", 0)

        userData = UserData(this)
        testData = TestData(this)

        viewModel = ViewModelProvider(this).get(TestingViewModel::class.java)

        viewModel.api = APIManager.create(userData.getToken())

        testData.getTestingAttempt()?.let {
            testingAttempt = it
        }
        currentTestIndex = testingAttempt.lastTestIndex

        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.testing_host_fragment, WaitingFragment.newInstance())
        ft.commit()

        if(id > 0){
            if(testingAttempt.testingCopy == null){
                viewModel.api.getTestingById(id).observe(this, {
                    tests.forEach { test->
                        testData.deleteAnswerSheets(test.id)
                    }
                    testingAttempt.tests = it.tests
                    testingAttempt.testingCopy = it.testing
                    testData.saveTestingAttempt(testingAttempt)
                    tests = it.tests
                    nextFragment()
                })
            } else {
                if(testingAttempt.testingCopy!!.id != id){
                    AlertDialog.createAndShow(applicationContext, "Нельзя сдавать два теcта за раз", "")
                    finish()
                }else{
                    tests = testingAttempt.tests
                    nextFragment()
                }
            }
        }
    }

    private fun nextFragment(){
        val index = currentTestIndex

        if(index == tests.size){
            testingEnd()
            return
        }

        if(lastFragmentType == FragmentType.REST){
            val fragment = TestFragment.newInstance(tests[index], object: NextButtonClicked{
                override fun click(){
                    nextFragment()
                }
            })
            // replace fragment
            ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.testing_host_fragment, fragment)
            ft.commit()
            // save last opened fragment in db
            testingAttempt.lastTestIndex = currentTestIndex
            currentTestIndex++
            lastFragmentType = FragmentType.TEST
        }else{
            val fragment = RestFragment.newInstance(object: NextButtonClicked{
                override fun click(){
                    nextFragment()
                }
            })
            ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.testing_host_fragment, fragment)
            ft.commit()
            lastFragmentType = FragmentType.REST
        }
        // save attempt instance  to db
        testData.saveTestingAttempt(testingAttempt)
    }

    private fun testingEnd(){
        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.testing_host_fragment, ResultFragment.newInstance(tests, object: NextButtonClicked{
            override fun click(){
                finish()
            }
        }))
        ft.commit()
    }

    override fun onPause() {
        super.onPause()
        // TODO save test begin time to db
    }
}