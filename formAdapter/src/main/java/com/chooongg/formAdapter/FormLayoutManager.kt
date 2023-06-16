package com.chooongg.formAdapter

import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FormLayoutManager(context: Context) : GridLayoutManager(context, 100) {

    private var recyclerView: RecyclerView? = null

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerView == null) {
                    100
                } else {
                    val adapter = recyclerView!!.adapter
                    val item = if (adapter is ConcatAdapter) {
                        val pair = adapter.getWrappedAdapterAndPosition(position)
                        if (pair.first is FormPartAdapter) {
                            (pair.first as FormPartAdapter).getItem(position)
                        } else null
                    } else if (adapter is FormPartAdapter) {
                        adapter.getItem(position)
                    } else null
                    if (item != null) {
                        100
                    } else 100
                }
            }
        }
    }

    override fun getPaddingStart() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingEnd() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingTop() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formVerticalGlobalMarginSize) ?: 0

    override fun getPaddingBottom() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formVerticalGlobalMarginSize) ?: 0

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        recyclerView = view
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        recyclerView = null
    }
}