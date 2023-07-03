package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial

object FormSwitchProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = FrameLayout(parent.context).apply {
        id = R.id.formInternalContent
        clipChildren = false
        clipToPadding = false
        val switch = MaterialSwitch(context).apply {
            id = R.id.formInternalContentChild
//            minimumHeight = 0
//            minHeight = 0
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        addView(switch)
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<MaterialSwitch>(R.id.formInternalContentChild)) {
            updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = typeset.getContentGravity(adapter, item)
            }
        }
    }
}