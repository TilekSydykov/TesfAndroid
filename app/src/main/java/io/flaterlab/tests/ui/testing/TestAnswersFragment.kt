package io.flaterlab.tests.ui.testing

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
import io.flaterlab.tests.data.model.Question

class TestAnswersFragment : Fragment() {

    companion object {
        fun newInstance(questions: ArrayList<Question>) = TestAnswersFragment().apply {
            this.questions = questions
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ApgradeAnswerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TestAnswersViewModel

    lateinit var questions: ArrayList<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_test_answers, container, false)

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = ApgradeAnswerAdapter(questions, requireContext(),
            object : AnswerSelectListener {
                override fun select(answerIndex: Int, variantNum: Int) {

                }
            })

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