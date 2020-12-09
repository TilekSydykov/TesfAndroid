package io.flaterlab.tests.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Test
import kotlinx.android.synthetic.main.fragment_test.view.*
import kotlinx.android.synthetic.main.item_test.*

class TestNotLoggedInFragment:  Fragment(){
    lateinit var test: Test

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        root.testName.text = test.name
        root.testInfo.text = test.info
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}