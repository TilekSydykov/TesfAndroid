package io.flaterlab.tests.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Attempt
import kotlinx.android.synthetic.main.item_attempt.view.*

class AttempttAdapter(private var myDataset: ArrayList<Attempt>, var context: Context)
    : RecyclerView.Adapter<AttempttAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_attempt, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val attempt: Attempt = myDataset[position]
        holder.constraintLayout.attemptResult.text = attempt.score.toString()
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<Attempt>){
        myDataset = list
        notifyDataSetChanged()
    }
}