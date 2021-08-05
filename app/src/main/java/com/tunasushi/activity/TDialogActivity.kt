package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchUpListener
import android.widget.Toast
import com.tunasushi.view.TDialog
import android.view.View

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TDialogActivity : Activity(), TouchUpListener {
    private lateinit var tViewConfirm: TView
    private lateinit var tViewChoice: TView
    private lateinit var tViewSelect: TView
    private lateinit var tDialogChoice: TDialog
    private lateinit var tDialogConfirm: TDialog
    private lateinit var tDialogSelect: TDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_dialog)

        //
        tViewConfirm = findViewById(R.id.tViewConfirm)
        tViewChoice = findViewById(R.id.tViewChoice)
        tViewSelect = findViewById(R.id.tViewSelect)
        tDialogConfirm = findViewById(R.id.tDialogConfirm)
        tDialogChoice = findViewById(R.id.tDialogChoice)
        tDialogSelect = findViewById(R.id.tDialogSelect)

        //
        tViewConfirm.setTouchUpListener(this)
        tViewChoice.setTouchUpListener(this)
        tViewSelect.setTouchUpListener(this)
        tDialogConfirm.setTouchUpListener(this)
        tDialogChoice.setTouchUpListener(this)
        tDialogSelect.setTouchUpListener(this)
    }

    override fun touchUp(t: TView) {
        when (t.id) {
            R.id.tViewConfirm -> tDialogConfirm.visibility = View.VISIBLE
            R.id.tViewChoice -> tDialogChoice.visibility = View.VISIBLE
            R.id.tViewSelect -> tDialogSelect.visibility = View.VISIBLE
            R.id.tDialogConfirm, R.id.tDialogChoice, R.id.tDialogSelect -> {
                val tDialog = t as TDialog
                val choiceIndex = tDialog.dialogChoiceIndex
                val valueArray = tDialog.dialogChoiceTextArray
                if (-1 == choiceIndex) {
                    Toast.makeText(this, "Nothing Choice", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, valueArray[choiceIndex], Toast.LENGTH_SHORT).show()
                    tDialog.visibility = View.INVISIBLE
                }
                tDialog.dialogChoiceIndex = -1
            }
            else -> {
            }
        }
    }
}