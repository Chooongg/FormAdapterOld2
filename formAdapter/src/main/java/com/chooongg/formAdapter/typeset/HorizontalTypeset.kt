package com.chooongg.formAdapter.typeset

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.formAdapter.FormManager
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.google.android.material.textview.MaterialTextView

object HorizontalTypeset : Typeset(FormManager.emsSize) {

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) =
        LinearLayoutCompat(parent.context).apply {
            clipChildren = false
            clipToPadding = false
            id = R.id.formInternalTypesetParent
            isBaselineAligned = false
            addView(MaterialTextView(context).apply {
                id = R.id.formInternalName
                setTextAppearance(R.style.FormAdapter_TextAppearance_Name)
                setPadding(
                    paddingInfo.horizontalLocal,
                    paddingInfo.verticalLocal,
                    paddingInfo.horizontalLocal,
                    paddingInfo.verticalLocal
                )
                layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            })
            layoutParams = GridLayoutManager.LayoutParams(
                GridLayoutManager.LayoutParams.MATCH_PARENT,
                GridLayoutManager.LayoutParams.WRAP_CONTENT
            )
        }

    override fun addView(parent: ViewGroup, view: View) {
        parent.addView(view, 1, view.layoutParams ?: LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_VERTICAL
        })
    }

    override fun onBindTypesetLayout(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalTypesetParent)) {
            setPaddingRelative(
                when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal
                    else -> 0
                },
                when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                    else -> 0
                },
                when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal
                    else -> 0
                },
                when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                    else -> 0
                }
            )
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalName)) {
            text = partAdapter.formAdapter.nameFormat.format(context, item.name, item.isMust)
            if (getContentGravity(partAdapter, item) and Gravity.END == Gravity.END) {
                minWidth = 0
                maxEms = ems
            } else setEms(ems)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HorizontalTypeset) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}