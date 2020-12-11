package io.flaterlab.tests.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import io.flaterlab.tests.R
import kotlinx.android.synthetic.main.dialog_alert.*

class AlertDialog(context: Context) : Dialog(context){
    lateinit var text1: String
    lateinit var text2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_alert)
        ok_button.setOnClickListener{
            dismiss()
        }
        editText1.text = text1
        editText2.text = text2
    }

    companion object{
        fun create(context: Context, t1: String, t2: String): AlertDialog {
            return AlertDialog(context).apply {
                text1 = t1
                text2 = t2
            }
        }

        fun createAndShow(context: Context, t1: String, t2: String): AlertDialog {
            val dialog = AlertDialog(context).apply {
                text1 = t1
                text2 = t2
            }
            dialog.show()
            return dialog
        }
    }
}