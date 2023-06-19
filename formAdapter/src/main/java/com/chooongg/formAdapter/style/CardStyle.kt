package com.chooongg.formAdapter.style

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.textview.MaterialTextView

class CardStyle(defaultTypeset: Typeset = HorizontalTypeset()) : Style(defaultTypeset) {

    override fun onCreateStyleLayout(parent: ViewGroup) = CardView(parent.context).apply {
        id = R.id.formInternalStyleParent
    }

    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) {

    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardStyle) return false
        if (!super.equals(other)) return false
        return true
    }

}