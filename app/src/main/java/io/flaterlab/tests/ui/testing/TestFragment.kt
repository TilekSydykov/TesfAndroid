package io.flaterlab.tests.ui.testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Test
import kotlinx.android.synthetic.main.test_fragment.view.*

class TestFragment : Fragment() {

    companion object {
        fun newInstance(test: Test) = TestFragment().apply {
            this.test = test
        }
    }

    lateinit var test: Test
    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.test_fragment, container, false)
        val sectionsPagerAdapter = TestPagerAdapter(requireContext(),
            requireActivity().supportFragmentManager).apply {
                questions = test.questions
            }

        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        root.test_title.text = test.name



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

    }

}