package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormDivider
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.divider.MaterialDivider

object FormDividerProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialDivider(parent.context).apply {
        id = R.id.formInternalContent
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = adapter.style.paddingInfo.verticalLocal
            bottomMargin = adapter.style.paddingInfo.verticalLocal
        }
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemDivider = item as? FormDivider
        with(holder.getView<MaterialDivider>(R.id.formInternalContent)) {
            if (itemDivider?.matchParentWidth == true) {
                dividerInsetStart = 0
                dividerInsetEnd = 0
                updateLayoutParams<MarginLayoutParams> {
                    marginStart =
                        -(adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal)
                    marginEnd =
                        -(adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal)
                }
            } else {
                dividerInsetStart = adapter.style.paddingInfo.horizontalLocal
                dividerInsetEnd = adapter.style.paddingInfo.horizontalLocal
                updateLayoutParams<MarginLayoutParams> {
                    marginStart = 0
                    marginEnd = 0
                }
            }
        }
    }

    override fun onBindItemViewForeground(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = null

    override fun onBindItemViewLongClick(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = Unit
}