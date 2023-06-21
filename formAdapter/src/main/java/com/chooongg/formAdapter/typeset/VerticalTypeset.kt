package com.chooongg.formAdapter.typeset

import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
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
        paddingInfo: FormPaddingInfo,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalTypesetParent)) {

        }
        with(holder.getView<MaterialTextView>(R.id.formInternalName)) {
            text = item.name
        }
    }
}