package com.chooongg.formAdapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class FormLayoutManager(context: Context) : GridLayoutManager(context, 100) {

    private var recyclerView: RecyclerView? = null

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerView == null) {
                    100
                } else {
                    val item = when (val adapter = recyclerView!!.adapter) {
                        is FormAdapter -> {
                            val pair = adapter.getWrappedAdapterAndPosition(position)
                            pair.first.getItem(pair.second)
                        }

                        is FormPartAdapter -> {
                            adapter.getItem(position)
                        }

                        else -> null
                    }
                    if (item != null) {
                        100
                    } else 100
                }
            }
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val smoothScroller = CenterSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    override fun getPaddingLeft() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingRight() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingStart() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingEnd() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize) ?: 0

    override fun getPaddingTop() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formVerticalGlobalMarginSize) ?: 0

    override fun getPaddingBottom() =
        recyclerView?.resources?.getDimensionPixelSize(R.dimen.formVerticalGlobalMarginSize) ?: 0

    private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int,
            viewEnd: Int,
            boxStart: Int,
            boxEnd: Int,
            snapPreference: Int
        ): Int {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
        }
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        recyclerView = view
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        recyclerView = null
    }
}