package io.flaterlab.tests.adapters;

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

class QuestionAdapter(private var myDataset: ArrayList<Question>, var context: Context, var listener: AnswerSelectListener, var attempt: Attempt)
    : RecyclerView.Adapter<QuestionAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_question, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question: Question = myDataset[position]
        val answer = attempt.answers[position]
        holder.constraintLayout.question.text = question.content
        val  variants = "А: " + question.one +
                "\nБ: " + question.two +
                "\nB: " + question.tree +
                "\nГ: " + question.four

        holder.constraintLayout.variants.text = variants

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