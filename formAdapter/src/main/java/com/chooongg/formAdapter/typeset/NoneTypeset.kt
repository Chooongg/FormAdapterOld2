package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm

object NoneTypeset : Typeset(0) {

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) = null

    override fun onBindTypesetLayout(
        paddingInfo: FormPaddingInfo,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.itemView.setPaddingRelative(
            when (item.marginBoundary.startType) {
                Boundary.LOCAL -> paddingInfo.horizontalLocal
                Boundary.GLOBAL -> paddingInfo.horizontalGlobal
                else -> 0
            }, when (item.marginBoundary.topType) {
                Boundary.LOCAL -> paddingInfo.verticalLocal
                Boundary.GLOBAL -> paddingInfo.verticalGlobal
                else -> 0
            }, when (item.marginBoundary.endType) {
                Boundary.LOCAL -> paddingInfo.horizontalLocal
                Boundary.GLOBAL -> paddingInfo.horizontalGlobal
                else -> 0
            }, when (item.marginBoundary.bottomType) {
                Boundary.LOCAL -> paddingInfo.verticalLocal
                Boundary.GLOBAL -> paddingInfo.verticalGlobal
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