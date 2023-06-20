package com.chooongg.formAdapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import kotlin.math.min

class FormLayoutManager(context: Context) : GridLayoutManager(context, 2520) {

    private var recyclerView: RecyclerView? = null

    var maxItemWidth: Int = FormManager.maxItemWidth
        set(value) {
            field = value
            if (recyclerView != null) {
                spanSize = min(10, recyclerView!!.measuredWidth / value)
            }
        }

    private var spanSize = 1

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = recyclerView?.adapter as? FormAdapter ?: return 2520
                val pair = adapter.getWrappedAdapterAndPosition(position)
                val item = pair.first.getItem(pair.second)
                return when (item) {
                    null -> 2520
                    is InternalFormGroupTitle -> 2520
                    else -> {
                        2520 / spanSize
                    }
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

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        spanSize = min(10, recyclerView!!.measuredWidth / maxItemWidth)
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        recyclerView = view
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler) {
        super.onDetachedFromWindow(view, recycler)
        recyclerView = null
    }

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
}