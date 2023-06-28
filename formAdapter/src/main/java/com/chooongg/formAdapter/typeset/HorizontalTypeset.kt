package com.chooongg.formAdapter.typeset

import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Layout
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
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
            id = R.id.formInternalTypesetParent
            clipChildren = false
            clipToPadding = false
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
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
        }

    override fun onBindTypesetLayout(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalTypesetParent)) {
            setPaddingRelative(
                when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal
                    else -> 0
                },
                when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal
                    else -> 0
                },
                when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal
                    else -> 0
                },
                when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal
                    else -> 0
                }
            )
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalName)) {
            text = adapter.formAdapter.nameFormat.format(context, item.name, item.isMust)
            if (item.isMustSingleColumn || adapter.formAdapter.normalColumnCount <= 1) {
                if (contentGravity() and Gravity.END == Gravity.END) {
                    minWidth = 0
                    maxEms = ems
                } else setEms(ems)
            } else {
                if (multiColumnContentGravity() and Gravity.END == Gravity.END) {
                    minWidth = 0
                    maxEms = ems
                } else setEms(ems)
            }
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