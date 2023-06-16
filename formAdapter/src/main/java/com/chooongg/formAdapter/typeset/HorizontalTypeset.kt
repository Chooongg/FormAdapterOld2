package com.chooongg.formAdapter.typeset

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.formAdapter.FormManager
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.enum.FormEmsMode
import com.chooongg.formAdapter.item.BaseForm
import com.google.android.material.textview.MaterialTextView

class HorizontalTypeset(
    ems: Int = FormManager.emsSize,
    emsMode: FormEmsMode = FormEmsMode.FIXED
) : Typeset(ems, emsMode) {

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) =
        LinearLayoutCompat(parent.context).apply {
            id = R.id.formInternalTypesetParent
            clipChildren = false
            clipToPadding = false
            showDividers = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
            dividerDrawable = object : ColorDrawable() {
                override fun getIntrinsicWidth() = paddingInfo.horizontalLocal
                override fun getIntrinsicHeight() = 0
            }
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(MaterialTextView(context).apply {
                id = R.id.formInternalName
                setTextAppearance(R.style.FormAdapter_TextAppearance_Name)
                layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            })
        }

    override fun onBindTypesetLayout(
        paddingInfo: FormPaddingInfo,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalTypesetParent)) {
            setPaddingRelative(
                when (item.marginBoundary.startType) {
                    Boundary.LOCAL-> paddingInfo.horizontalLocal
                    Boundary.GLOBAL-> paddingInfo.horizontalGlobal
                    else -> 0
                }, when (item.marginBoundary.topType) {
                    Boundary.LOCAL-> paddingInfo.verticalLocal
                    Boundary.GLOBAL-> paddingInfo.verticalGlobal
                    else -> 0
                }, when (item.marginBoundary.endType) {
                    Boundary.LOCAL-> paddingInfo.horizontalLocal
                    Boundary.GLOBAL-> paddingInfo.horizontalGlobal
                    else -> 0
                }, when (item.marginBoundary.bottomType) {
                    Boundary.LOCAL-> paddingInfo.verticalLocal
                    Boundary.GLOBAL-> paddingInfo.verticalGlobal
                    else -> 0
                }
            )
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalName)) {
            text = item.name
            setNameEms(this)
        }
    }
}