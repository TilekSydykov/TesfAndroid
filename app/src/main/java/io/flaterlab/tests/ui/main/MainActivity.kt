package io.flaterlab.tests.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.adapters.TestAdapter
import io.flaterlab.tests.data.UserData
import io.flaterlab.tests.data.api.APIManager

class MainActivity : AppCompatActivity() {

    lateinit var userData: UserData
    lateinit var api: APIManager

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TestAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = TestAdapter(arrayListOf() , this)
        recyclerView = findViewById<RecyclerView>(R.id.tests).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        userData = UserData(this)

        api = APIManager.create(userData.getToken())

        api.paginateTest(1).observe(this, Observer {
            it?.let {
                viewAdapter.update(it.data)
            }
        })

    }
}