package io.flaterlab.tests.ui.testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
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

    lateinit var nextButtonClicked: NextButtonClicked

    companion object {
        fun newInstance(test: Test, nextButtonClicked: NextButtonClicked) = TestFragment().apply {
            this.nextButtonClicked = nextButtonClicked
            this.test = test
        }
    }

    lateinit var test: Test
    private lateinit var viewModel: TestViewModel
    lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.test_fragment, container, false)
        val sectionsPagerAdapter = TestPagerAdapter(requireContext(),
            requireActivity().supportFragmentManager).apply {
                this.t = test
                this.nextButton = object : NextButtonClicked{
                    override fun click() {
                        timer.cancel()
                        nextButtonClicked.click()
                    }
                }
            }
        beginTimer(root)
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

    fun beginTimer(root: View){
        timer = object : CountDownTimer((test.duration!! * 60 * 1000), 1000){
            override fun onTick(millis: Long){
                root.time_left.text = getTimeLeft((millis/1000))
            }
            override fun onFinish(){
                nextButtonClicked.click()
            }
        }.start()
    }

    fun getTimeLeft(timeSeconds: Long): String {
        var minutes = (timeSeconds/60).toInt().toString()
        var seconds = (timeSeconds % 60).toString()
        if(minutes.length < 2){
            minutes = "0$minutes"
        }
        if(seconds.length < 2){
            seconds = "0$seconds"
        }
        return "$minutes:$seconds"
    }
}