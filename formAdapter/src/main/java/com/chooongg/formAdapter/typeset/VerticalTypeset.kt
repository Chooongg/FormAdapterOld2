package com.chooongg.formAdapter.typeset

import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
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
            clipChildren = false
            clipToPadding = false
            id = R.id.formInternalTypesetParent
            orientation = LinearLayoutCompat.VERTICAL
            showDividers = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
            dividerDrawable = object : ColorDrawable() {
                override fun getIntrinsicWidth() = paddingInfo.horizontalLocal
                override fun getIntrinsicHeight() = 0
            }
            addView(MaterialTextView(context).apply {
                id = R.id.formInternalName
                setTextAppearance(R.style.FormAdapter_TextAppearance_Name)
                setPadding(
                    paddingInfo.horizontalLocal,
                    paddingInfo.verticalLocal,
                    paddingInfo.horizontalLocal,
                    0
                )
                layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            })
            layoutParams = GridLayoutManager.LayoutParams(
                GridLayoutManager.LayoutParams.MATCH_PARENT,
                GridLayoutManager.LayoutParams.WRAP_CONTENT
            )
        }

    override fun addView(parent: ViewGroup, view: View) {
        parent.addView(
            view, view.layoutParams ?: FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        )
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
        }
    }
}