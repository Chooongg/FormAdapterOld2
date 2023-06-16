package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.textview.MaterialTextView

object FormTextProvider : BaseFormProvider() {

    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.getView<MaterialTextView>(R.id.formInternalContent).apply {
            text = item.getContentText()
            gravity = typeset.contentGravity()
            updateLayoutParams<ViewGroup.LayoutParams> {
                width = typeset.contentWidth()
            }
        }
    }
}