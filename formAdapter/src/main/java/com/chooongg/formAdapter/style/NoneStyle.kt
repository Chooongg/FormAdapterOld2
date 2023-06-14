package com.chooongg.formAdapter.style

import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.google.android.material.textview.MaterialTextView

object NoneStyle : Style(NoneTypeset) {

    override fun isNeedDecorationMargins() = false
    override fun onCreateStyleLayout(parent: ViewGroup) = null
    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) = Unit
    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formContent)) {
            text = item.name
        }
    }
}