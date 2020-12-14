package io.flaterlab.tests.ui.testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.flaterlab.tests.R
import io.flaterlab.tests.data.TestData
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Question
import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.data.model.local.AnswerSheet
import kotlinx.android.synthetic.main.result_fragment.view.*

class ResultFragment : Fragment() {

    companion object {
        fun newInstance(tests: ArrayList<Test>, nextButtonClicked: NextButtonClicked) = ResultFragment().apply {
            this.tests = tests
            this.nextButtonClicked = nextButtonClicked
        }
    }

    private lateinit var tests: ArrayList<Test>

    private lateinit var nextButtonClicked: NextButtonClicked

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.result_fragment, container, false)
        val testData = TestData(requireContext())

        var resultText = ""

        var allRights: Long = 0
        var full: Long = 0

        tests.forEach {
            val answerSheet = testData.getAnswerSheets(it.id)
            resultText += it.name + ":"

            var amountRightAnswers = 0
            var score = 0.toLong()
            it.questions.forEachIndexed { index: Int, question: Question ->
                if(answerSheet.answers[index].questionId == question.id){
                    if(answerSheet.answers[index].variant == question.right) {
                        score += question.weight
                        amountRightAnswers++
                        allRights += question.weight
                    }
                    full += question.weight
                }else{
                    answerSheet.answers.forEach { answer ->
                        if(answer.questionId == question.id){
                            if(answerSheet.answers[index].variant == question.right) {
                                score += question.weight
                                amountRightAnswers++
                                allRights += question.weight
                            }
                            full += question.weight
                        }
                    }
                }
            }
            resultText += " $amountRightAnswers / ${it.questions.size} >> $score \n"

        }
        resultText += "Общий балл: $allRights из $full"
        root.results.text = resultText
        root.ok_button.setOnClickListener {
            testData.deleteTestingAttempt()
            nextButtonClicked.click()
        }

        // sending result to server
        val userData = UserData(requireContext())
        val api = APIManager.create(userData.getToken())
        api.addScore(testData.getTestingAttempt()!!.testingCopy!!.id, allRights).observe(requireActivity(), {
            root.ok_button.visibility = View.VISIBLE
        })

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)

    }
}