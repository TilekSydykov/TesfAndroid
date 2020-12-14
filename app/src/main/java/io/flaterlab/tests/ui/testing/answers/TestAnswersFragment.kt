package io.flaterlab.tests.ui.testing.answers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.AnswerSelectListener
import io.flaterlab.tests.adapters.ApgradeAnswerAdapter
import io.flaterlab.tests.data.TestData
import io.flaterlab.tests.data.model.Answer
import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.data.model.local.AnswerSheet
import io.flaterlab.tests.dialogs.NextTestDialog
import io.flaterlab.tests.ui.testing.NextButtonClicked
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_test_answers.*
import kotlinx.android.synthetic.main.fragment_test_answers.view.*

class TestAnswersFragment : Fragment() {

    companion object {
        fun newInstance(test: Test, nextButtonClicked: NextButtonClicked) = TestAnswersFragment().apply {
            this.test = test
            this.nextButtonClicked = nextButtonClicked
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ApgradeAnswerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TestAnswersViewModel

    private lateinit var testData: TestData
    private lateinit var answerSheet: AnswerSheet
    lateinit var test: Test

    lateinit var nextButtonClicked: NextButtonClicked

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_test_answers, container, false)

        testData = TestData(requireContext())

        answerSheet = testData.getAnswerSheets(test.id)

        if(answerSheet.answers.size == 0){
            answerSheet = AnswerSheet()
            test.questions.forEach {
                answerSheet.answers.add(Answer().apply {
                    questionId = it.id
                })
            }
            answerSheet.testId = test.id
            testData.saveAnswerSheets(answerSheet!!)
        }

        root.nextButton.setOnClickListener {
            NextTestDialog(requireContext()).apply {
                this.yesButtonListener = nextButtonClicked
            }.show()
        }

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = ApgradeAnswerAdapter(test.questions, requireContext(),
            object : AnswerSelectListener {
                override fun select(answerIndex: Int, variantNum: Int) {
                    answerSheet.answers[answerIndex].variant = variantNum.toLong()
                    testData.saveAnswerSheets(answerSheet)
                }
            }, answerSheet.answers)

        recyclerView = root.findViewById<RecyclerView>(R.id.answersRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestAnswersViewModel::class.java)

    }

}