package io.flaterlab.tests.adapters;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.flaterlab.tests.R
import io.flaterlab.tests.data.model.Testing
import io.flaterlab.tests.dialogs.BeginTestDialog
import io.flaterlab.tests.dialogs.BeginTestListener
import io.flaterlab.tests.ui.testing.TestingActivity
import kotlinx.android.synthetic.main.item_test.view.*

class TestingAdapter(private var myDataset: ArrayList<Testing>, var context: Context)
    : RecyclerView.Adapter<TestingAdapter.MyViewHolder>() {
    class MyViewHolder(val constraintLayout: View) : RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val constraintLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_test, parent, false) as ConstraintLayout
        return MyViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val test: Testing = myDataset[position]
        holder.constraintLayout.testName.text = test.name

        holder.constraintLayout.setOnClickListener {
            val d = BeginTestDialog(context).apply {
                yesButtonListener = object : BeginTestListener {
                    override fun begin(dialog: BeginTestDialog) {
                        val z = Intent(context, TestingActivity::class.java)
                        z.putExtra("id", test.id)
                        z.putExtra("name", test.name)
                        context.startActivity(z)
                        dialog.dismiss()
                    }
                }
            }
            d.show()
        }
    }

    override fun getItemCount() = myDataset.size

    fun update(list : ArrayList<Testing>){
        myDataset = list
        notifyDataSetChanged()
    }
}