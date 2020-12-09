package io.flaterlab.tests.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.AttempttAdapter
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Test
import kotlinx.android.synthetic.main.fragment_test.view.*

class TestFragment:  Fragment(){
    lateinit var test: Test
    lateinit var attempts: ArrayList<Attempt>
    lateinit var beginListener: View.OnClickListener

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AttempttAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        root.testName.text = test.name
        root.testInfo.text = test.info
        root.test_time.text = "${test.duration} + :00"
        root.begin_button.setOnClickListener(beginListener)

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = AttempttAdapter(attempts, requireActivity())

        recyclerView = root.findViewById<RecyclerView>(R.id.attemptRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}