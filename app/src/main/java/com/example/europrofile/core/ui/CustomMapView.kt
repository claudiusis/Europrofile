package com.example.europrofile.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.yandex.mapkit.mapview.MapView

class CustomMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapView(context, attrs, defStyleAttr) {

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // Запретить ScrollView перехватывать события касания.
                this.parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP -> {
                // Разрешить ScrollView перехватывать события касания.
                this.parent?.requestDisallowInterceptTouchEvent(false)
            }
        }

        // Обработать события касания MapView.
        return super.onTouchEvent(ev)
    }
}
