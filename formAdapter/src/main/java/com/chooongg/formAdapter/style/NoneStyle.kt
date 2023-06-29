package com.chooongg.formAdapter.style

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.FormMarginInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.google.android.material.textview.MaterialTextView

object NoneStyle : Style(HorizontalTypeset) {

    override fun onCreateMarginInfo(context: Context): FormMarginInfo {
        return FormMarginInfo(0, 0, 0, 0)
    }

    override fun onCreateStyleLayout(parent: ViewGroup) = null
    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) = Unit

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = if (item.name != null) {
                item.name
            } else {
                val defaultName = holder.itemView.resources.getString(R.string.formDefaultGroupName)
                if (item.isDynamicPart) {
                    "$defaultName ${item.groupIndex + 1}"
                } else defaultName
            }
            updatePaddingRelative(
                paddingInfo.horizontalLocal,
                paddingInfo.verticalLocal,
                paddingInfo.horizontalLocal,
                paddingInfo.verticalLocal
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NoneStyle) return false
        if (!super.equals(other)) return false
        return true
    }
}