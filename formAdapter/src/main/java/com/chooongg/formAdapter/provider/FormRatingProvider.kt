package com.chooongg.formAdapter.provider

import android.content.res.ColorStateList
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormRating
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor

object FormRatingProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = FrameLayout(parent.context).apply {
        clipChildren = false
        clipToPadding = false
        id = R.id.formInternalContent
        val rating = AppCompatRatingBar(
            context, null, com.google.android.material.R.attr.ratingBarStyleIndicator
        ).apply {
            id = R.id.formInternalContentChild
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        addView(rating)
        setPadding(
            adapter.style.paddingInfo.horizontalLocal, 0,
            adapter.style.paddingInfo.horizontalLocal, 0
        )
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemRating = item as? FormRating
        with(holder.getView<AppCompatRatingBar>(R.id.formInternalContentChild)) {
            setIsIndicator(!item.isRealEnable(adapter.formAdapter))
            numStars = itemRating?.numStars ?: 5
            stepSize = itemRating?.stepSize ?: 1f
            onRatingBarChangeListener = null
            rating = (item.content as? Float) ?: 0f
            progressTintList = itemRating?.tint?.invoke(context) ?: ColorStateList.valueOf(
                attrColor(androidx.appcompat.R.attr.colorPrimary)
            )
            setOnRatingBarChangeListener { _, value, _ ->
                changeContentAndNotifyLinkage(adapter, item, value)
                rating = value
            }
            updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = typeset.getContentGravity(adapter, item)
            }
        }
    }
}