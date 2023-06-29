package com.chooongg.formAdapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.boundary.Boundary
import kotlin.math.max
import kotlin.math.min

class FormLayoutManager(context: Context) : GridLayoutManager(context, 2520) {

    private var recyclerView: RecyclerView? = null

    var maxItemWidth: Int = FormManager.maxItemWidth
        set(value) {
            field = value
            if (recyclerView != null) {
                normalColumnCount = max(1, min(10, recyclerView!!.measuredWidth / maxItemWidth))
            }
        }

    internal var normalColumnCount = 1
        set(value) {
            field = value
            (recyclerView?.adapter as? FormAdapter)?.normalColumnCount = normalColumnCount
        }

    private var paddingStart: Int = 0
    private var paddingEnd: Int = 0

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = recyclerView?.adapter as? FormAdapter ?: return spanCount
                val pair = adapter.getWrappedAdapterAndPosition(position)
                val item = pair.first.getItem(pair.second)
                item.globalPosition = position
                return item.itemSpan
            }

            override fun getSpanIndex(position: Int, spanCount: Int): Int {
                val adapter = recyclerView?.adapter as? FormAdapter ?: return 0
                val pair = adapter.getWrappedAdapterAndPosition(position)
                return pair.first.getItem(pair.second).spanIndex
            }
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (!state.isPreLayout) calculateBoundary()
        super.onLayoutChildren(recycler, state)
    }

    private fun calculateBoundary() {
        Log.e("Form", "calculateBoundary")
        val formAdapter = recyclerView?.adapter as? FormAdapter ?: return
        var spanIndex = 0
        formAdapter.partAdapters.forEachIndexed { partIndex, adapter ->
            adapter.getItemList().forEachIndexed { index, item ->
                item.itemSpan = if (item.isSingleLineItem) {
                    item.itemSpan
                } else if (item.isMustSingleColumn) {
                    spanCount
                } else {
                    val span = spanCount / normalColumnCount
                    if (item.nextItemIsSingleColumn || (index >= adapter.getItemList().lastIndex && spanIndex + span < spanCount)) {
                        spanCount - spanIndex
                    } else span
                }
                item.spanIndex = spanIndex
                if (spanIndex == 0) {
                    item.marginBoundary.startType = Boundary.GLOBAL
                    item.paddingBoundary.startType = Boundary.GLOBAL
                } else {
                    item.marginBoundary.startType = Boundary.NONE
                    item.paddingBoundary.startType =
                        if (item.isSingleLineItem) FormManager.singleLineDividerType else FormManager.horizontalDividerType
                }
                spanIndex += item.itemSpan
                if (spanIndex == spanCount) {
                    item.marginBoundary.endType = Boundary.GLOBAL
                    item.paddingBoundary.endType = Boundary.GLOBAL
                    spanIndex = 0
                } else {
                    item.marginBoundary.endType = Boundary.NONE
                    item.paddingBoundary.endType =
                        if (item.isSingleLineItem) FormManager.singleLineDividerType else FormManager.horizontalDividerType
                }
                if (item.positionForGroup == 0) {
                    if (partIndex == 0 && index == 0) {
                        item.marginBoundary.topType = Boundary.GLOBAL
                        item.paddingBoundary.topType = Boundary.GLOBAL
                    } else {
                        item.marginBoundary.topType = Boundary.LOCAL
                        item.paddingBoundary.topType = Boundary.GLOBAL
                    }
                } else if (item.spanIndex != 0) {
                    var lastIndex = index - 1
                    while (adapter.getItemList()[lastIndex].spanIndex != 0) {
                        lastIndex--
                    }
                    item.marginBoundary.topType =
                        adapter.getItemList()[lastIndex].marginBoundary.topType
                    item.paddingBoundary.topType =
                        adapter.getItemList()[lastIndex].paddingBoundary.topType
                } else {
                    item.marginBoundary.topType = Boundary.NONE
                    item.paddingBoundary.topType = Boundary.LOCAL
                }
            }
            for (index in adapter.getItemList().lastIndex downTo 0) {
                val item = adapter.getItemList()[index]
                if (item.itemCountForGroup - item.positionForGroup == 1) {
                    if (partIndex == formAdapter.partSize() - 1 && index == adapter.getItemList().lastIndex) {
                        item.marginBoundary.bottomType = Boundary.GLOBAL
                        item.paddingBoundary.bottomType = Boundary.GLOBAL
                    } else {
                        item.marginBoundary.bottomType = Boundary.LOCAL
                        item.paddingBoundary.bottomType = Boundary.GLOBAL
                    }
                } else if (item.spanIndex + item.itemSpan != spanCount) {
                    var beginIndex = index + 1
                    while (adapter.getItemList()[beginIndex].spanIndex + adapter.getItemList()[beginIndex].itemSpan != spanCount) {
                        beginIndex++
                    }
                    item.marginBoundary.bottomType =
                        adapter.getItemList()[beginIndex].marginBoundary.bottomType
                    item.paddingBoundary.bottomType =
                        adapter.getItemList()[beginIndex].paddingBoundary.bottomType
                } else {
                    item.marginBoundary.bottomType = Boundary.NONE
                    item.paddingBoundary.bottomType = Boundary.LOCAL
                }
            }
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State, position: Int
    ) {
        val smoothScroller = CenterSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    fun setPadding(start: Int, end: Int) {
        paddingStart = start
        paddingEnd = end
    }

    override fun getPaddingStart() = super.getPaddingStart() + paddingEnd
    override fun getPaddingEnd() = super.getPaddingEnd() + paddingEnd
    override fun getPaddingLeft() =
        super.getPaddingLeft() + if (isLayoutRTL) paddingEnd else paddingStart

    override fun getPaddingRight() =
        super.getPaddingRight() + if (isLayoutRTL) paddingStart else paddingEnd

    override fun onMeasure(
        recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        normalColumnCount = max(1, min(10, recyclerView!!.measuredWidth / maxItemWidth))
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
            viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
        ): Int {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
        }
    }
}