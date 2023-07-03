package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormSelector
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
        parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
    ).apply {
        id = R.id.formInternalContent
        minimumHeight = 0
        minimumWidth = 0
        minHeight = 0
        minWidth = 0
        insetBottom = 0
        insetTop = 0
        iconTint = ColorStateList.valueOf(hintTextColors.defaultColor)
        iconGravity = MaterialButton.ICON_GRAVITY_END
        setIconResource(R.drawable.ic_form_arrow_down)
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setPadding(
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal,
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal
        )
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    @SuppressLint("ResourceType")
    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemSelector = item as? FormSelector
        val drawable = DeterminateDrawable.createCircularDrawable(
            holder.itemView.context,
            CircularProgressIndicatorSpec(holder.itemView.context, null)
        )
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(adapter.formAdapter)
            text = item.getContentText()
            hint = item.hint ?: resources.getString(R.string.fromDefaultHintNone)
            gravity = typeset.getContentGravity(adapter, item)
            iconSize = itemSelector?.iconSize
                ?: context.resources.getDimensionPixelSize(R.dimen.formIconSize)
            updateLayoutParams<ViewGroup.LayoutParams> {
                width = typeset.contentWidth()
            }
//            icon = drawable
//            drawable.start()
        }
    }

    override fun onItemRecycler(holder: FormViewHolder) {
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            if (it.icon is DeterminateDrawable<*>) {
                (it.icon as DeterminateDrawable<*>).stop()
            }
        }
    }
}