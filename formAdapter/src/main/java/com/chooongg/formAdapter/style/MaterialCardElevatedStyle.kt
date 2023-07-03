package com.chooongg.formAdapter.style

import android.content.res.ColorStateList
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
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

class MaterialCardElevatedStyle(
    val customElevation: Float? = null,
    defaultTypeset: Typeset = HorizontalTypeset
) : Style(defaultTypeset) {

    override fun onBindStyle(adapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        holder.itemView.isEnabled = item.isRealEnable(adapter.formAdapter)
        holder.itemView.clipToOutline = true
        holder.itemView.updateLayoutParams<MarginLayoutParams> {
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
        val shape = getShapeAppearanceModel(holder, item)
        holder.itemView.elevation = customElevation ?: holder.itemView.attrChildDimensionPixelSize(
            com.google.android.material.R.attr.materialCardViewElevatedStyle,
            com.google.android.material.R.attr.cardElevation, 0
        ).toFloat()
        val shapeDrawable = MaterialShapeDrawable(shape).apply {
            val provider = ElevationOverlayProvider(holder.itemView.context)
            fillColor = ColorStateList.valueOf(
                provider.compositeOverlay(provider.themeSurfaceColor, holder.itemView.elevation)
            )
        }
        holder.itemView.background = shapeDrawable
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
        if (other !is MaterialCardElevatedStyle) return false
        if (!super.equals(other)) return false

        if (customElevation != other.customElevation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (customElevation?.hashCode() ?: 0)
        return result
    }
}