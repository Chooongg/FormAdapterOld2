package com.chooongg.formAdapter.style

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrChildDimensionPixelSize
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView

open class CardFilledStyle(
    val color: FormColorStateListBlock? = null,
    defaultTypeset: Typeset = HorizontalTypeset
) : Style(defaultTypeset) {

    override fun onBindStyle(adapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        holder.itemView.clipToOutline = true
        holder.itemView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = when (item.marginBoundary.topType) {
                Boundary.GLOBAL -> marginInfo.verticalGlobal
                Boundary.LOCAL -> marginInfo.verticalLocal
                else -> 0
            }
            bottomMargin = when (item.marginBoundary.bottomType) {
                Boundary.GLOBAL -> marginInfo.verticalGlobal
                Boundary.LOCAL -> marginInfo.verticalLocal
                else -> 0
            }
        }
        val shape = getShapeAppearanceModel(holder, item).configShapeAppearanceModel(holder, item)
        if (holder.itemView.background is MaterialShapeDrawable) {
            (holder.itemView.background as MaterialShapeDrawable).shapeAppearanceModel = shape
        } else holder.itemView.background = MaterialShapeDrawable(shape)
        val provider = ElevationOverlayProvider(holder.itemView.context)
        (holder.itemView.background as MaterialShapeDrawable).fillColor =
            color?.invoke(holder.itemView.context) ?: ColorStateList.valueOf(
                provider.compositeOverlay(
                    provider.themeSurfaceColor,
                    holder.itemView.attrChildDimensionPixelSize(
                        com.google.android.material.R.attr.materialCardViewElevatedStyle,
                        com.google.android.material.R.attr.cardElevation, 0
                    ).toFloat()
                )
            )
    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
            updatePaddingRelative(
                paddingInfo.horizontalLocal,
                paddingInfo.verticalLocal,
                paddingInfo.horizontalLocal,
                paddingInfo.verticalLocal
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardFilledStyle) return false
        if (!super.equals(other)) return false

        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (color?.hashCode() ?: 0)
        return result
    }
}