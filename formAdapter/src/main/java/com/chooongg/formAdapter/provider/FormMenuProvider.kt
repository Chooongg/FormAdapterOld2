package com.chooongg.formAdapter.provider

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormMenu
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.attrDrawable
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.gone
import com.chooongg.utils.ext.resDimensionPixelSize
import com.chooongg.utils.ext.resDrawable
import com.chooongg.utils.ext.visible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

object FormMenuProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = LinearLayoutCompat(parent.context).apply {
        id = R.id.formInternalContent
        orientation = LinearLayoutCompat.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        addView(AppCompatImageView(context).apply {
            id = R.id.formInternalContentChildIcon
            adjustViewBounds = true
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = adapter.style.paddingInfo.horizontalLocal
            }
        })
        addView(MaterialTextView(context).apply {
            id = R.id.formInternalContentChildName
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal,
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal
            )
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        })
        addView(MaterialTextView(context).apply {
            id = R.id.formInternalContentChild
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal,
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal
            )
            gravity = Gravity.END
            layoutParams = LinearLayoutCompat.LayoutParams(
                0, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f
            )
        })
        addView(AppCompatImageView(context).apply {
            id = R.id.formInternalContentChildMoreIcon
            adjustViewBounds = true
            val iconSize = resDimensionPixelSize(R.dimen.formIconSize)
            setImageDrawable(resDrawable(R.drawable.ic_form_arrow_end)?.apply {
                setBounds(0, 0, iconSize, iconSize)
            })
            imageTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(-android.R.attr.state_enabled)
                ),
                intArrayOf(
                    attrColor(com.google.android.material.R.attr.colorOnSurface),
                    attrColor(com.google.android.material.R.attr.colorOutline)
                )
            )
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = adapter.style.paddingInfo.horizontalLocal
            }
        })
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
        holder.itemView.doOnClick {

        }
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalContent)) {
            isEnabled = item.isRealEnable(adapter.formAdapter)
            background = if (parent.parent !is MaterialCardView) {
                attrDrawable(android.R.attr.selectableItemBackground)
            } else null
            setPadding(
                when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal
                    else -> 0
                }, when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal
                    else -> 0
                }, when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal
                    else -> 0
                }, when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal
                    else -> 0
                }
            )
            updateLayoutParams<MarginLayoutParams> {
                marginStart = when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> -(adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal)
                    else -> 0
                }
                marginEnd = when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> -(adapter.style.paddingInfo.horizontalGlobal - adapter.style.paddingInfo.horizontalLocal)
                    else -> 0
                }
                topMargin = when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> -(adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal)
                    else -> 0
                }
                bottomMargin = when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> -(adapter.style.paddingInfo.verticalGlobal - adapter.style.paddingInfo.verticalLocal)
                    else -> 0
                }
            }
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalContentChildName)) {
            text = item.name
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalContentChild)) {
            text = item.content?.toString()
            maxLines = 2
        }
        val itemMenu = item as? FormMenu
        with(holder.getView<AppCompatImageView>(R.id.formInternalContentChildIcon)) {
            if (itemMenu?.iconRes != null) {
                val iconSize = item.iconSize ?: resDimensionPixelSize(R.dimen.formIconSize)
                setImageDrawable(resDrawable(itemMenu.iconRes!!)?.apply {
                    setBounds(0, 0, iconSize, iconSize)
                })
                imageTintList = itemMenu.iconTint?.invoke(context)
                visible()
            } else gone()
        }
        with(holder.getView<AppCompatImageView>(R.id.formInternalContentChildMoreIcon)) {
            if (itemMenu?.isShowMore == true) visible() else gone()
        }
    }
}