package io.flaterlab.tests.ui.testing

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Test
import io.flaterlab.tests.ui.testing.answers.TestAnswersFragment
import io.flaterlab.tests.ui.testing.questions.TestQuestionsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class TestPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    lateinit var t: Test
    lateinit var nextButton: NextButtonClicked

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return TestQuestionsFragment.newInstance(t.questions)
            1 -> return TestAnswersFragment.newInstance(t, nextButton)
        }
        return RestFragment.newInstance(nextButton)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}