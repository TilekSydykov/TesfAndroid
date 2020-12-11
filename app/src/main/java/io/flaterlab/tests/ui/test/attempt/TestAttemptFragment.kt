package io.flaterlab.tests.ui.test.attempt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.AnswerSelectListener
import io.flaterlab.tests.adapters.QuestionAdapter
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.model.Answer
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Test
import kotlinx.android.synthetic.main.fragment_attempt_test.view.*
import kotlin.properties.Delegates

class TestAttemptFragment:  Fragment(){
    lateinit var test: Test
    lateinit var attempt: Attempt
    var attemptIndex by Delegates.notNull<Int>()
    lateinit var userData: UserData

    lateinit var testFinishedInterface: TestFinishedInterface

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: QuestionAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_attempt_test, container, false)
        viewManager = LinearLayoutManager(requireActivity())
        userData = UserData(requireContext())

        root.testNameTitle.text = test.name

        viewAdapter = QuestionAdapter(test.questions, requireActivity(), object : AnswerSelectListener {
            override fun select(answerIndex: Int, variantNum: Int) {
                val a = Answer()
                a.variant = variantNum.toLong()
                if(test.questions[answerIndex].right == variantNum.toLong()){
                    a.score = test.questions[answerIndex].weight
                }
                attempt.answers[answerIndex] = a
                userData.replaceAttemptByIndex(test.id, attemptIndex, attempt)
            }
        }, attempt)

        root.finishButton.setOnClickListener {
            countResults()

        }

        recyclerView = root.findViewById<RecyclerView>(R.id.testRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return root
    }

    private fun countResults(){
        attempt.score = 0
        attempt.answers.forEach {
            attempt.score = attempt.score?.plus(it.score)
        }
        attempt.isEnded = true
        testFinishedInterface.finish(attempt)
    }
}