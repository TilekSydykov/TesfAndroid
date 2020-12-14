package io.flaterlab.tests.ui.testing

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.flaterlab.tests.R
import kotlinx.android.synthetic.main.fragment_rest.*
import kotlinx.android.synthetic.main.fragment_rest.view.*
import kotlinx.android.synthetic.main.test_fragment.view.*

class RestFragment : Fragment() {

    lateinit var nextButtonClicked: NextButtonClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var timer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_rest, container, false)

        root.nextButton.setOnClickListener {
            nextButtonClicked.click()
            timer.cancel()
        }

        beginTimer(root)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(nextButtonClicked: NextButtonClicked) =
            RestFragment().apply {
                this.nextButtonClicked = nextButtonClicked
            }
    }

    private fun beginTimer(root: View){
        timer = object : CountDownTimer((5 * 60 * 1000), 1000){
            override fun onTick(millis: Long){
                root.time.text = getTimeLeft((millis/1000))
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