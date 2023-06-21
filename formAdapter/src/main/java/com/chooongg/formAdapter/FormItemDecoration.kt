package com.chooongg.formAdapter

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutParams

class FormItemDecoration:ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount){
            val view = parent[i]
            getSpanIndex(parent,view,view.layoutParams as LayoutParams)
        }
    }

    fun getSpanIndex(parent: RecyclerView, view: View, layoutParams: LayoutParams) {
//        val currentPosition = parent.getChildAdapterPosition(view)
        if (layoutParams is GridLayoutManager.LayoutParams) {
            val currentPosition = parent.getChildAdapterPosition(view)
            Log.e("Form","${currentPosition}项SpanIndex：${layoutParams.spanIndex}")
        }
    }
}