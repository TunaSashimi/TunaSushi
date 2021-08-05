package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.graphics.EmbossMaskFilter
import android.graphics.BlurMaskFilter
import com.tunasushi.view.TDraw
import com.tunasushi.view.TPickerDialog
import android.graphics.PorterDuffXfermode
import android.graphics.PorterDuff
import android.view.Menu
import android.view.MenuItem

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TDrawActivity : Activity() {
    private var embossMaskFilter: EmbossMaskFilter? = null
    private var blurMaskFilter: BlurMaskFilter? = null
    private var tDraw: TDraw? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_draw)
        embossMaskFilter = EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6F, 3.5f)
        blurMaskFilter = BlurMaskFilter(8F, BlurMaskFilter.Blur.NORMAL)
        tDraw = findViewById(R.id.tDraw)
        tDraw?.setPaintingListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, COLOR_MENU_ID, 1, "Color")
        menu.add(Menu.NONE, EMBOSS_MENU_ID, 2, "Emboss")
        menu.add(Menu.NONE, BLUR_MENU_ID, 3, "Blur")
        menu.add(Menu.NONE, ERASE_MENU_ID, 4, "Erase")
        menu.add(Menu.NONE, SRCATOP_MENU_ID, 5, "SrcATop")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        tDraw!!.paintingPaint.xfermode = null
        tDraw!!.paintingPaint.alpha = 0xFF
        when (item.itemId) {
            COLOR_MENU_ID -> {
                val colorPicker = TPickerDialog(
                    this,
                    tDraw!!.paintingPaint.color
                ) { color -> tDraw!!.paintingPaint.color = color }
                colorPicker.show()
                return true
            }
            EMBOSS_MENU_ID -> {
                if (tDraw!!.paintingPaint.maskFilter !== embossMaskFilter) {
                    tDraw!!.paintingPaint.maskFilter = embossMaskFilter
                } else {
                    tDraw!!.paintingPaint.maskFilter = null
                }
                return true
            }
            BLUR_MENU_ID -> {
                if (tDraw!!.paintingPaint.maskFilter !== blurMaskFilter) {
                    tDraw!!.paintingPaint.maskFilter = blurMaskFilter
                } else {
                    tDraw!!.paintingPaint.maskFilter = null
                }
                return true
            }
            ERASE_MENU_ID -> {
                tDraw!!.paintingPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                return true
            }
            SRCATOP_MENU_ID -> {
                tDraw!!.paintingPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
                tDraw!!.paintingPaint.alpha = 0x80
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val COLOR_MENU_ID = Menu.FIRST
        private const val EMBOSS_MENU_ID = Menu.FIRST + 1
        private const val BLUR_MENU_ID = Menu.FIRST + 2
        private const val ERASE_MENU_ID = Menu.FIRST + 3
        private const val SRCATOP_MENU_ID = Menu.FIRST + 4
    }
}