package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.ArrayList

/**
 * @author TunaSashimi
 * @date 2020-02-09 18:45
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */

class EntryActivity : Activity() {
    private val cla = arrayOf<Class<*>>(
        TViewActivity::class.java,
        TAnalysisActivity::class.java,
        TArcActivity::class.java,
        TBezierActivity::class.java,
        TBindingActivity::class.java,
        TBubbleActivity::class.java,
        TButtonActivity::class.java,
        TDialogActivity::class.java,
        TDrawActivity::class.java,
        THollowActivity::class.java,
        TImageActivity::class.java,
        TLayoutActivity::class.java,
        TLineActivity::class.java,
        TLoadActivity::class.java,
        TMaterialActivity::class.java,
        TMoveActivity::class.java,
        TPathActivity::class.java,
        TPatternActivity::class.java,
        TPentagramActivity::class.java,
        TProgressActivity::class.java,
        TRangeActivity::class.java,
        TRepeatActivity::class.java,
        TRippleActivity::class.java,
        TScaleActivity::class.java,
        TScratchActivity::class.java,
        TSectorActivity::class.java,
        TSeekActivity::class.java,
        TSignActivity::class.java,
        TSVGActivity::class.java,
        TToggleActivity::class.java,
        TTrackActivity::class.java,
        TTrangleActivity::class.java,
        TWaveActivity::class.java,
        TWrapActivity::class.java,
        TestActivity::class.java
    )

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val listView = findViewById<ListView>(R.id.listView)
        val stringList: MutableList<String?> = ArrayList()
        for (i in cla.indices) {
            stringList.add(cla[i].simpleName)
        }
        listView.adapter = ArrayAdapter(this, R.layout.activity_entry_item, stringList)
        listView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            startActivity(
                Intent(
                    this@EntryActivity,
                    cla[arg2]
                )
            )
        }
    }
}