package io.flaterlab.tests.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.AttempttAdapter
import io.flaterlab.tests.adapters.TestingAdapter
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager
import io.flaterlab.tests.ui.login.LoginActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var data: UserData

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TestingAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        data = UserData(requireContext())

        homeViewModel.api = APIManager.create(data.getToken())

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = TestingAdapter(arrayListOf(), requireContext())

        recyclerView = root.findViewById<RecyclerView>(R.id.testingsRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        homeViewModel.api.getTestings().observe(viewLifecycleOwner, {
            if(it.error != null){
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
            viewAdapter.update(it.data)
        })

        return root
    }
}