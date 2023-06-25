package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.style.NoneStyle.paddingInfo

object NoneTypeset : Typeset(0) {

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) = null

    override fun onBindTypesetLayout(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val view = holder.getViewOrNull<ViewGroup>(R.id.formInternalStyleParent) ?: holder.itemView
        view.setPaddingRelative(
            when (item.paddingBoundary.startType) {
                Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal
                Boundary.LOCAL -> adapter.style.paddingInfo.horizontalLocal
                else -> 0
            },
            when (item.paddingBoundary.topType) {
                Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal
                Boundary.LOCAL -> adapter.style.paddingInfo.verticalLocal
                else -> 0
            },
            when (item.paddingBoundary.endType) {
                Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal
                Boundary.LOCAL -> adapter.style.paddingInfo.horizontalLocal
                else -> 0
            },
            when (item.paddingBoundary.bottomType) {
                Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal
                Boundary.LOCAL -> adapter.style.paddingInfo.verticalLocal
                else -> 0
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoneTypeset) return false
        if (!super.equals(other)) return false
        return true
    }
}