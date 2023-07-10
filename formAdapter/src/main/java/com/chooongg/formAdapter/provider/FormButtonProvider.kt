package com.chooongg.formAdapter.provider

import android.animation.AnimatorInflater
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormButton
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.doOnLongClick
import com.google.android.material.button.MaterialButton
import com.google.android.material.theme.overlay.MaterialThemeOverlay

object FormButtonProvider : BaseFormProvider() {
    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialButton(parent.context).apply {
        id = R.id.formInternalContent
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal - insetTop,
                partAdapter.style.paddingInfo.horizontalLocal,
                partAdapter.style.paddingInfo.verticalLocal - insetBottom
            )
        }
    }

    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemButton = item as? FormButton
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealEnable(partAdapter.formAdapter)
            text = item.name
            hint = item.hint
            if (itemButton?.iconRes != null) {
                setIconResource(itemButton.iconRes!!)
            } else icon = null
            iconSize = itemButton?.iconSize
                ?: resources.getDimensionPixelSize(R.dimen.formIconSize)
            iconGravity = itemButton?.iconGravity ?: MaterialButton.ICON_GRAVITY_TEXT_START
            configButtonStyle(this, itemButton)
            configButtonGravity(this, item.contentGravity ?: Gravity.NO_GRAVITY)
        }
    }

    private fun configButtonStyle(view: MaterialButton, itemButton: FormButton?) {
        val style = when (itemButton?.buttonStyle ?: FormButton.ButtonStyle.DEFAULT) {
            FormButton.ButtonStyle.DEFAULT -> view.context.obtainStyledAttributes(
                intArrayOf(com.google.android.material.R.attr.materialButtonStyle)
            ).use { it.getResourceId(0, 0) }

            FormButton.ButtonStyle.TEXT -> view.context.obtainStyledAttributes(
                intArrayOf(com.google.android.material.R.attr.borderlessButtonStyle)
            ).use { it.getResourceId(0, 0) }

            FormButton.ButtonStyle.TONAL ->
                com.google.android.material.R.style.Widget_Material3_Button_TonalButton

            FormButton.ButtonStyle.OUTLINED -> view.context.obtainStyledAttributes(
                intArrayOf(com.google.android.material.R.attr.materialButtonOutlinedStyle)
            ).use { it.getResourceId(0, 0) }

            FormButton.ButtonStyle.ELEVATED ->
                com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton

            FormButton.ButtonStyle.UN_ELEVATED ->
                com.google.android.material.R.style.Widget_Material3_Button_UnelevatedButton
        }
        val wrap = MaterialThemeOverlay.wrap(view.context, null, 0, style)
        view.setTextColor(wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.textColor)
        ).use { it.getColorStateList(0) })
        view.iconTint = if (itemButton?.iconTint == null) {
            wrap.obtainStyledAttributes(
                style, intArrayOf(androidx.appcompat.R.attr.iconTint)
            ).use { it.getColorStateList(0) }
        } else itemButton.iconTint!!.invoke(view.context)
        view.backgroundTintList = wrap.obtainStyledAttributes(
            style, intArrayOf(androidx.appcompat.R.attr.backgroundTint)
        ).use { it.getColorStateList(0) }
        view.strokeColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeColor)
        ).use { it.getColorStateList(0) }
        view.strokeWidth = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.strokeWidth)
        ).use { it.getDimensionPixelSize(0, 0) }
        view.rippleColor = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.rippleColor)
        ).use { it.getColorStateList(0) }
        view.elevation = wrap.obtainStyledAttributes(
            style, intArrayOf(com.google.android.material.R.attr.elevation)
        ).use { it.getDimension(0, 0f) }

        val stateListId = wrap.obtainStyledAttributes(
            style, intArrayOf(android.R.attr.stateListAnimator)
        ).use { it.getResourceId(0, 0) }
        view.stateListAnimator = if (stateListId != 0) {
            AnimatorInflater.loadStateListAnimator(wrap, stateListId)
        } else null
    }

    private fun configButtonGravity(view: MaterialButton, contentGravity: Int) {
        if (contentGravity == Gravity.NO_GRAVITY) {
            view.updateLayoutParams<ViewGroup.LayoutParams> {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            when (view.parent) {
                is FrameLayout -> view.updateLayoutParams<FrameLayout.LayoutParams> {
                    gravity = Gravity.NO_GRAVITY
                }

                is LinearLayout -> view.updateLayoutParams<LinearLayout.LayoutParams> {
                    gravity = Gravity.NO_GRAVITY
                }

                is LinearLayoutCompat -> view.updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                    gravity = Gravity.NO_GRAVITY
                }
            }
        } else {
            view.updateLayoutParams<ViewGroup.LayoutParams> {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            when (view.parent) {
                is FrameLayout -> view.updateLayoutParams<FrameLayout.LayoutParams> {
                    gravity = contentGravity
                }

                is LinearLayout -> view.updateLayoutParams<LinearLayout.LayoutParams> {
                    gravity = contentGravity
                }

                is LinearLayoutCompat -> view.updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                    gravity = contentGravity
                }
            }
        }
    }

    override fun onBindItemViewForeground(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = null

    override fun onBindItemViewLongClick(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.getView<MaterialButton>(R.id.formInternalContent).doOnLongClick {
            false
        }
    }
}