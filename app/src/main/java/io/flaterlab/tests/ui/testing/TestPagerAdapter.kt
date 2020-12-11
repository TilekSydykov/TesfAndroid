package io.flaterlab.tests.ui.testing

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Answer
import io.flaterlab.tests.data.model.Question

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class TestPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    lateinit var questions: ArrayList<Question>
    lateinit var answers: ArrayList<Answer>

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return TestQuestionsFragment.newInstance(questions)
            1 -> return TestAnswersFragment.newInstance(questions)
        }
        return RestFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}