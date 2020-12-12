package io.flaterlab.tests.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import io.flaterlab.tests.R
import io.flaterlab.tests.ui.testing.NextButtonClicked
import kotlinx.android.synthetic.main.dialog_alert.*

class NextTestDialog(context: Context) : Dialog(context){
    lateinit var yesButtonListener: NextButtonClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_begin_test)
        ok_button.setOnClickListener {
            yesButtonListener.click()
            dismiss()
        }
        cancel_button.setOnClickListener {
            dismiss()
        }
    }
}