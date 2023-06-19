package com.chooongg.formAdapter.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.Typeset

object FormGroupTitleProvider : BaseFormProvider() {

    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ): View = adapter.style.onCreateGroupTitle(parent)

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        if (item is InternalFormGroupTitle) adapter.style.onBindGroupTitle(holder, item)
    }
}