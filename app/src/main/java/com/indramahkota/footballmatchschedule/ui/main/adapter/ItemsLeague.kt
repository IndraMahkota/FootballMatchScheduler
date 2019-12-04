package com.indramahkota.footballmatchschedule.ui.main.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class ItemsLeague : AnkoComponent<ViewGroup>{

    companion object {
        const val clubImage = 1
        const val clubName = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.HORIZONTAL
            padding = dip(16)

            imageView {
                id = clubImage
            }.lparams(width = dip(50), height = dip(50))

            textView {
                id = clubName
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER_VERTICAL
                margin = dip(10)
            }
        }
    }
}