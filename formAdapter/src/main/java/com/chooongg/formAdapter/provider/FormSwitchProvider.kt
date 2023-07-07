package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.materialswitch.MaterialSwitch

object FormSwitchProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = FrameLayout(parent.context).apply {
        clipChildren = false
        clipToPadding = false
        id = R.id.formInternalContent
        val switch = MaterialSwitch(context).apply {
            clipChildren = false
            clipToPadding = false
            id = R.id.formInternalContentChild
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal,
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal,
            )
            minimumHeight = 0
            minHeight = 0
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        addView(switch)
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<MaterialSwitch>(R.id.formInternalContentChild)) {
            isEnabled = item.isRealEnable(adapter.formAdapter)
            setOnCheckedChangeListener(null)
            isChecked = item.content as? Boolean ?: false
            setOnCheckedChangeListener { _, isCheck ->
                changeContentAndNotifyLinkage(adapter, item, isCheck)
            }
            updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = typeset.getContentGravity(adapter, item)
            }
        }
    }
}