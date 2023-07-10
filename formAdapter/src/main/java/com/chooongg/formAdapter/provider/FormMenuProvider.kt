package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
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
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.dp2px
import com.chooongg.utils.ext.gone
import com.chooongg.utils.ext.resDimensionPixelSize
import com.chooongg.utils.ext.resDrawable
import com.chooongg.utils.ext.visible
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView

object FormMenuProvider : BaseFormProvider() {

    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = LinearLayoutCompat(parent.context).apply {
        id = R.id.formInternalContent
        clipChildren = false
        clipToPadding = false
        orientation = LinearLayoutCompat.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        addView(AppCompatImageView(context).apply {
            id = R.id.formInternalContentChildIcon
            adjustViewBounds = true
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = partAdapter.style.paddingInfo.horizontalLocal
            }
        })
        addView(MaterialTextView(context).apply {
            id = R.id.formInternalContentChildName
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal,
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal
            )
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        })
        addView(MaterialTextView(context).apply {
            id = R.id.formInternalContentChild
            setTextAppearance(R.style.FormAdapter_TextAppearance_Tip)
            setTextColor(
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_enabled),
                        intArrayOf(-android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        attrColor(com.google.android.material.R.attr.colorPrimary),
                        attrColor(com.google.android.material.R.attr.colorOutline)
                    )
                )
            )
            setPadding(
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal,
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal
            )
            gravity = Gravity.END
            layoutParams = LinearLayoutCompat.LayoutParams(
                0, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f
            )
        })
        addView(MaterialTextView(context).apply {
            id = R.id.formInternalContentChildBadge
            setBackgroundResource(R.drawable.background_form_badge)
            setTextColor(attrColor(com.google.android.material.R.attr.colorOnError))
            setSingleLine()
            minWidth = dp2px(16f)
            minHeight = dp2px(16f)
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8f)
            setPadding(dp2px(4f), dp2px(2f), dp2px(4f), dp2px(2f))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
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
                marginEnd = partAdapter.style.paddingInfo.horizontalLocal
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemMenu = item as? FormMenu
        holder.itemView.doOnClick {

        }
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalContent)) {
            isEnabled = item.isRealEnable(partAdapter.formAdapter)
            setPadding(
                when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal
                    else -> 0
                }, if (itemMenu?.isGlobalPadding == true) {
                    partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                } else when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                    else -> 0
                }, when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal
                    else -> 0
                }, if (itemMenu?.isGlobalPadding == true) {
                    partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                } else when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal
                    else -> 0
                }
            )
            updateLayoutParams<MarginLayoutParams> {
                marginStart = when (item.paddingBoundary.startType) {
                    Boundary.GLOBAL -> -(partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal)
                    else -> 0
                }
                marginEnd = when (item.paddingBoundary.endType) {
                    Boundary.GLOBAL -> -(partAdapter.style.paddingInfo.horizontalGlobal - partAdapter.style.paddingInfo.horizontalLocal)
                    else -> 0
                }
                topMargin = when (item.paddingBoundary.topType) {
                    Boundary.GLOBAL -> -(partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal)
                    else -> 0
                }
                bottomMargin = when (item.paddingBoundary.bottomType) {
                    Boundary.GLOBAL -> -(partAdapter.style.paddingInfo.verticalGlobal - partAdapter.style.paddingInfo.verticalLocal)
                    else -> 0
                }
            }
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalContentChildName)) {
            text = item.name
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalContentChild)) {
            isEnabled = item.isRealEnable(partAdapter.formAdapter)
            text = item.content?.toString()
            maxLines = 2
        }
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
        with(holder.getView<MaterialTextView>(R.id.formInternalContentChildBadge)) {
            if (itemMenu?.badgeText != null) {
                text = itemMenu.badgeText!!
                visible()
            } else if (itemMenu?.badgeNumber != null) {
                text =
                    if (itemMenu.badgeMaxNumber == FormMenu.NO_SET || itemMenu.badgeNumber!! <= itemMenu.badgeMaxNumber) {
                        itemMenu.badgeNumber!!.toString()
                    } else {
                        "${itemMenu.badgeMaxNumber}+"
                    }
                visible()
            } else {
                gone()
            }
        }
        with(holder.getView<AppCompatImageView>(R.id.formInternalContentChildMoreIcon)) {
            if (itemMenu?.isShowMore == true) visible() else gone()
        }
    }

    override fun onBindItemViewForeground(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = RippleDrawable(
        ColorStateList.valueOf(
            holder.itemView.attrColor(androidx.appcompat.R.attr.colorControlHighlight)
        ),
        null,
        holder.itemView.background ?: MaterialShapeDrawable(
            partAdapter.style.getShapeAppearanceModel(partAdapter)
        )
    )

    override fun onBindItemViewLongClick(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = Unit
}