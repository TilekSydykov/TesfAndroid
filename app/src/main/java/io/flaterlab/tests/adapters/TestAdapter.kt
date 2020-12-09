package io.flaterlab.tests.adapters;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.ui.test.TestActivity
import io.flaterlab.tests.data.model.Test
import kotlinx.android.synthetic.main.item_test.view.*

class TestAdapter(private var myDataset: ArrayList<Test>, var context: Context)
    : RecyclerView.Adapter<TestAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_test, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val test: Test = myDataset[position]
        holder.constraintLayout.testName.text = test.name

        holder.constraintLayout.setOnClickListener {
            val z = Intent(context, TestActivity::class.java)
            z.putExtra("id", test.id)
            z.putExtra("name", test.name)
            context.startActivity(z)
        }
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<Test>){
        myDataset = list
        notifyDataSetChanged()
    }
}