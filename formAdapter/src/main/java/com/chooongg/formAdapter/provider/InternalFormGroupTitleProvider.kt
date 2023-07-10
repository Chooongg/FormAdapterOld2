package com.chooongg.formAdapter.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.Typeset

object InternalFormGroupTitleProvider : BaseFormProvider() {

    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ): View = partAdapter.style.onCreateGroupTitle(parent)

    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        if (item is InternalFormGroupTitle) partAdapter.style.onBindGroupTitle(holder, item)
    }
}