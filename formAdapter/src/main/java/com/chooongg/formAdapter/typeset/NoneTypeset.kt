package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm

object NoneTypeset : Typeset(0) {

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) =
        FrameLayout(parent.context).apply {
            clipChildren = false
            clipToPadding = false
            id = R.id.formInternalTypesetParent
            layoutParams = MarginLayoutParams(
                MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
            )
        }

    override fun onBindTypesetLayout(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<FrameLayout>(R.id.formInternalTypesetParent)) {
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
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoneTypeset) return false
        if (!super.equals(other)) return false
        return true
    }
}