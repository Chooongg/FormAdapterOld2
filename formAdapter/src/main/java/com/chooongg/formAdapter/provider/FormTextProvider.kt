package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.google.android.material.textview.MaterialTextView

object FormTextProvider : BaseFormProvider() {

    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setHintTextColor(attrColor(android.R.attr.textColorHint))
        setPadding(
            partAdapter.style.paddingInfo.horizontalLocal,
            partAdapter.style.paddingInfo.verticalLocal,
            partAdapter.style.paddingInfo.horizontalLocal,
            partAdapter.style.paddingInfo.verticalLocal
        )
        alpha = 0.8f
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.getView<MaterialTextView>(R.id.formInternalContent).apply {
            text = item.getContentText(partAdapter, holder)
            hint = item.hint ?: resources.getString(R.string.fromDefaultHintNone)
            gravity = typeset.getContentGravity(partAdapter, item)
        }
    }
}