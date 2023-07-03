package com.chooongg.formAdapter.style

import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.FormMarginInfo
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.utils.ext.attrColor
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView

object NoneStyle : Style(HorizontalTypeset) {

    override fun onCreateMarginInfo(context: Context): FormMarginInfo {
        return FormMarginInfo(0, 0, 0, 0)
    }

    override fun onCreatePaddingInfo(context: Context): FormPaddingInfo {
        return FormPaddingInfo(
            context.resources.getDimensionPixelSize(R.dimen.formVerticalGlobalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formVerticalGlobalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalLocalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalGlobalPaddingSize)
        )
    }

    override fun onBindStyle(adapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        holder.itemView.isEnabled = item.isRealEnable(adapter.formAdapter)
        holder.itemView.clipToOutline = false
        holder.itemView.background = null
    }

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