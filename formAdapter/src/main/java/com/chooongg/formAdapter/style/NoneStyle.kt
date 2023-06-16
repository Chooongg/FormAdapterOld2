package com.chooongg.formAdapter.style

import android.content.Context
import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.FormMarginInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.google.android.material.textview.MaterialTextView

object NoneStyle : Style(HorizontalTypeset()) {

    override fun onCreateMarginInfo(context: Context): FormMarginInfo {
        return FormMarginInfo(0, 0, 0, 0)
    }

    override fun onCreateStyleLayout(parent: ViewGroup) = null
    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) {

    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
        }
    }
}