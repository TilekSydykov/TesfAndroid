package io.flaterlab.tests.ui.testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.flaterlab.tests.R

class TestAnswersFragment : Fragment() {

    companion object {
        fun newInstance() = TestAnswersFragment()
    }

    private lateinit var viewModel: TestAnswersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_answers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestAnswersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}