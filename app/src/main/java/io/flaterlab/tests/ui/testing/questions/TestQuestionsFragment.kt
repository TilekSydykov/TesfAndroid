package io.flaterlab.tests.ui.testing.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.ApgradeQuestionAdapter
import io.flaterlab.tests.data.model.Question

class TestQuestionsFragment : Fragment() {

    companion object {
        fun newInstance(questions: ArrayList<Question>) = TestQuestionsFragment().apply {
            this.questions = questions
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ApgradeQuestionAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TestQuestionsViewModel
    lateinit var questions: ArrayList<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_test_questions, container, false)

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = ApgradeQuestionAdapter(questions, requireContext())

        recyclerView = root.findViewById<RecyclerView>(R.id.questionsRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestQuestionsViewModel::class.java)

    }

}