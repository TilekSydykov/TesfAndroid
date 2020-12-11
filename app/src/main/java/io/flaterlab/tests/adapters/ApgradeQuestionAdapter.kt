package io.flaterlab.tests.adapters;

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Question
import kotlinx.android.synthetic.main.item_question.view.*

class ApgradeQuestionAdapter(private var myDataset: ArrayList<Question>, var context: Context)
    : RecyclerView.Adapter<ApgradeQuestionAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_apgrade_question, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question: Question = myDataset[position]
        holder.constraintLayout.question.text = (position + 1).toString()+ ". " + question.content
        val  variants = "А: " + question.one +
                "\nБ: " + question.two +
                "\nB: " + question.tree +
                "\nГ: " + question.four

        holder.constraintLayout.variants.text = variants
    }

    override fun getItemCount() = myDataset.size

    fun update(list: ArrayList<Question>){
        myDataset = list
        notifyDataSetChanged()
    }
}