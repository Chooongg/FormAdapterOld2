package com.chooongg.formAdapter.style

import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.textview.MaterialTextView

class MaterialCardOutlinedStyle(
    defaultTypeset: Typeset = HorizontalTypeset
) : Style(defaultTypeset) {

    override fun onBindStyle(partAdapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        TODO("Not yet implemented")
    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
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
        if (other !is CardElevatedStyle) return false
        if (!super.equals(other)) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}