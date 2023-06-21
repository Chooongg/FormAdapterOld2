package com.chooongg.formAdapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(context: Context) : GridLayoutManager(context, 2520) {

    private var recyclerView: RecyclerView? = null

    var maxItemWidth: Int = FormManager.maxItemWidth
        set(value) {
            field = value
            if (recyclerView != null) {
                normalSpanSize = max(1, min(10, recyclerView!!.measuredWidth / maxItemWidth))
            }
        }

    private var normalSpanSize = 1

    private var paddingVertical: Int = 0
    private var paddingHorizontal: Int = 0

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                Log.e("Form", "getSpanSize: $position")
                val adapter = recyclerView?.adapter as? FormAdapter ?: return 2520
                val pair = adapter.getWrappedAdapterAndPosition(position)
                val partIndex = adapter.partIndexOf(pair.first)
                val partAdapter = pair.first
                val item = pair.first.getItem(pair.second)
                val itemSpan = when (item) {
                    null -> 2520
                    is InternalFormGroupTitle -> 2520
                    else -> {
                        if (item.isSingleLineItem) {
                            item.singleLineItemSpan
                        } else 2520 / normalSpanSize
                    }
                }
                item.marginBoundary.topType =
                    if (item.positionForGroup == 0) {
                        if (partIndex == 0 && item.groupIndex == 0) Boundary.GLOBAL else Boundary.LOCAL
                    } else if (item.isSingleLineItem) {
                        partAdapter.getItem(pair.second - item.singleLineIndex).marginBoundary.topType
                    } else {
                        Boundary.NONE
                    }
                item.marginBoundary.bottomType =
                    if (item.positionForGroup == item.itemCountForGroup - 1) {
                        if (partIndex == adapter.partSize() - 1 && item.groupIndex == item.groupCount - 1) Boundary.GLOBAL else Boundary.LOCAL
                    } else if (item.isSingleLineItem) {
                        partAdapter.getItem(pair.second + item.singleLineCount - item.singleLineIndex - 1).marginBoundary.bottomType
                    } else {
                        Boundary.NONE
                    }
                return itemSpan
            }

            override fun getSpanGroupIndex(adapterPosition: Int, spanCount: Int): Int {
                Log.e("Form", "getSpanGroupIndex: $adapterPosition")
                return super.getSpanGroupIndex(adapterPosition, spanCount)
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                Log.e("Form", "getSpanIndex: $position")
                val index = super.getSpanIndex(position, spanCount)
                val adapter = recyclerView?.adapter as? FormAdapter ?: return index
                val pair = adapter.getWrappedAdapterAndPosition(position)
                val partAdapter = pair.first
                val item = partAdapter.getItem(pair.second)
                val itemSpan = when (item) {
                    null -> 2520
                    is InternalFormGroupTitle -> 2520
                    else -> {
                        if (item.isSingleLineItem) {
                            item.singleLineItemSpan
                        } else 2520 / normalSpanSize
                    }
                }
                item.marginBoundary.startType =
                    if (index == 0) {
                        Boundary.GLOBAL
                    } else {
                        Boundary.NONE
                    }
                item.marginBoundary.endType =
                    if (index + itemSpan == spanCount) {
                        Boundary.GLOBAL
                    } else {
                        Boundary.NONE
                    }
                return index
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

    fun setPadding(vertical: Int, horizontal: Int) {
        paddingVertical = vertical
        paddingHorizontal = horizontal
    }

    override fun getPaddingLeft() =
        if (paddingHorizontal <= 0) super.getPaddingLeft() else paddingHorizontal

    override fun getPaddingRight() =
        if (paddingHorizontal <= 0) super.getPaddingRight() else paddingHorizontal

    override fun getPaddingStart() =
        if (paddingHorizontal <= 0) super.getPaddingStart() else paddingHorizontal

    override fun getPaddingEnd() =
        if (paddingHorizontal <= 0) super.getPaddingEnd() else paddingHorizontal

    override fun getPaddingTop() =
        if (paddingVertical <= 0) super.getPaddingTop() else paddingVertical

    override fun getPaddingBottom() =
        if (paddingVertical <= 0) super.getPaddingBottom() else paddingVertical

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        normalSpanSize = max(1, min(10, recyclerView!!.measuredWidth / maxItemWidth))
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