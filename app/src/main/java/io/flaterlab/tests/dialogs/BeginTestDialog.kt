package io.flaterlab.tests.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import io.flaterlab.tests.R
import kotlinx.android.synthetic.main.dialog_alert.*

class BeginTestDialog(context: Context) : Dialog(context){
    lateinit var yesButtonListener: BeginTestListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_begin_test)
        ok_button.setOnClickListener {
            yesButtonListener.begin(this)
        }
        cancel_button.setOnClickListener {
            dismiss()
        }
    }
}