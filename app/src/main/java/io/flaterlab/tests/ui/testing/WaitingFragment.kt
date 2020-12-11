package io.flaterlab.tests.ui.testing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.flaterlab.tests.R

class WaitingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment().apply {}
    }
}