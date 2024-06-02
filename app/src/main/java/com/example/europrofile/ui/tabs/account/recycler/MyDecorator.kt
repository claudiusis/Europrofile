package com.example.europrofile.ui.tabs.account.recycler

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView


class MyDecorator() : RecyclerView.ItemDecoration() {

    private var dividerDrawable: Drawable? = null

    fun setDrawable(drawable: Drawable?) {
        dividerDrawable = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom: Int = dividerTop + dividerDrawable?.intrinsicHeight!!
            dividerDrawable?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            dividerDrawable?.draw(c)
        }
    }
}
