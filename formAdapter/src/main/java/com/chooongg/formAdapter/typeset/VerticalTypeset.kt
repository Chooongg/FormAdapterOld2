package com.chooongg.formAdapter.typeset

import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.google.android.material.textview.MaterialTextView

object VerticalTypeset : Typeset() {

    override fun contentGravity() = Gravity.NO_GRAVITY

    override fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo) =
        LinearLayoutCompat(parent.context).apply {
            id = R.id.formInternalTypesetParent
            orientation = LinearLayoutCompat.VERTICAL
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
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
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
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal
                    Boundary.LOCAL -> adapter.style.paddingInfo.horizontalGlobal
                    else -> 0
                },
                when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal
                    Boundary.LOCAL -> adapter.style.paddingInfo.verticalLocal
                    else -> 0
                },
                when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal
                    Boundary.LOCAL -> adapter.style.paddingInfo.horizontalGlobal
                    else -> 0
                },
                when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal
                    Boundary.LOCAL -> adapter.style.paddingInfo.verticalLocal
                    else -> 0
                }
            )
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalName)) {
            text = item.name
        }
    }
}