package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.DeterminateDrawable

object FormSelectorProvider : BaseFormProvider() {

    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialButton(
        parent.context,
        null,
        com.google.android.material.R.style.Widget_Material3_Button_TextButton
    ).apply {
        id = R.id.formInternalContent
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
        val drawable = DeterminateDrawable.createCircularDrawable(
            holder.itemView.context,
            CircularProgressIndicatorSpec(holder.itemView.context, null)
        )
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            it.icon = drawable
            drawable.start()
        }
        drawable.stop()
    }

    override fun onItemRecycler(holder: FormViewHolder) {
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            if (it.icon is DeterminateDrawable<*>) {
                (it.icon as DeterminateDrawable<*>).stop()
            }
        }
    }
}