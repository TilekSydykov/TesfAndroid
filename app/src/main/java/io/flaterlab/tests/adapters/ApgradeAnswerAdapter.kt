package io.flaterlab.tests.adapters;

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Answer
import io.flaterlab.tests.data.model.Attempt
import io.flaterlab.tests.data.model.Question
import kotlinx.android.synthetic.main.item_question.view.*

class ApgradeAnswerAdapter(private var myDataset: ArrayList<Question>,
                           var context: Context,
                           var listener: AnswerSelectListener,
                            var answers: ArrayList<Answer>)
    : RecyclerView.Adapter<ApgradeAnswerAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_answer, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.constraintLayout.question.text = (position + 1).toString()+ ". "
        val answer = answers[position]

        when (answer.variant.toInt()) {
            1 -> {
                holder.constraintLayout.one.isChecked = true
            }
            2 -> {
                holder.constraintLayout.two.isChecked = true
            }
            3 -> {
                holder.constraintLayout.three.isChecked = true
            }
            4 -> {
                holder.constraintLayout.four.isChecked = true
            }
        }
        holder.constraintLayout.one.setOnClickListener {
            listener.select(position, 1)
        }
        holder.constraintLayout.two.setOnClickListener {
            listener.select(position, 2)
        }
        holder.constraintLayout.three.setOnClickListener {
            listener.select(position, 3)
        }
        holder.constraintLayout.four.setOnClickListener {
            listener.select(position, 4)
        }
    }

    override fun getItemCount() = myDataset.size

    fun update(list: ArrayList<Question>){
        myDataset = list
        notifyDataSetChanged()
    }
}