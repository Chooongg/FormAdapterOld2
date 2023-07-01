package com.chooongg.formAdapter.provider

import android.view.Gravity
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormTip
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.google.android.material.textview.MaterialTextView

object FormTipProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(R.style.FormAdapter_TextAppearance_Tip)
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemTip = item as? FormTip
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
            hint = item.hint ?: resources.getString(R.string.fromDefaultHintNone)
            gravity = item.contentGravity ?: Gravity.NO_GRAVITY
            if (itemTip?.color != null) {
                setTextColor(itemTip.color!!.invoke(context))
            } else {
                setTextColor(context.attrColor(com.google.android.material.R.attr.colorOutline))
            }
            setPadding(
                adapter.style.paddingInfo.horizontalLocal,
                if (itemTip?.enableTopPadding == true) adapter.style.paddingInfo.verticalLocal else 0,
                adapter.style.paddingInfo.horizontalLocal,
                if (itemTip?.enableBottomPadding == true) adapter.style.paddingInfo.verticalLocal else 0
            )
        }
    }
}