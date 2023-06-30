package com.chooongg.formAdapter.style

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.dp2px
import com.google.android.material.textview.MaterialTextView

class CardStyle(defaultTypeset: Typeset = HorizontalTypeset) : Style(defaultTypeset) {

    override fun onCreateStyleLayout(parent: ViewGroup) = CardView(parent.context).apply {
        clipChildren = false
        clipToPadding = false
        id = R.id.formInternalStyleParent
        radius = dp2px(8f).toFloat()
        layoutParams = MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) {
        holder.getView<CardView>(R.id.formInternalStyleParent).let {
            it.updateLayoutParams<MarginLayoutParams> {
                topMargin = when (item.marginBoundary.topType) {
                    Boundary.LOCAL -> marginInfo.verticalLocal
                    else -> 0
                }
                bottomMargin = when (item.marginBoundary.bottomType) {
                    Boundary.LOCAL -> marginInfo.verticalLocal
                    else -> 0
                }
            }
        }
    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
            updatePaddingRelative(
                paddingInfo.horizontalGlobal,
                paddingInfo.verticalGlobal,
                paddingInfo.horizontalGlobal,
                paddingInfo.verticalGlobal
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardStyle) return false
        if (!super.equals(other)) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}