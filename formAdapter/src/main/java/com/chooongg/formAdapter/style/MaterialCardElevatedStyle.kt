package com.chooongg.formAdapter.style

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.GridLayoutManager
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.HorizontalTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView

class MaterialCardElevatedStyle(
    val elevation: Float? = null,
    defaultTypeset: Typeset = HorizontalTypeset
) : Style(defaultTypeset) {

    override fun onCreateStyleLayout(parent: ViewGroup) = MaterialCardView(
        parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
    ).apply {
        id = R.id.formInternalStyleParent
        tag = shapeAppearanceModel.toBuilder().build()
        layoutParams = GridLayoutManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            outlineSpotShadowColor = Color.TRANSPARENT
            outlineAmbientShadowColor = Color.TRANSPARENT
        }
    }

    override fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm) {
        holder.getView<MaterialCardView>(R.id.formInternalStyleParent).let {
            if (elevation != null) it.cardElevation = elevation
            val originalShape = it.tag as? ShapeAppearanceModel ?: return
            val builder = it.shapeAppearanceModel.toBuilder()
            if (it.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (item.marginBoundary.topType != 0 && item.marginBoundary.startType != 0) {
                    builder.setTopLeftCornerSize(originalShape.topLeftCornerSize)
                } else builder.setTopLeftCornerSize(0f)
                if (item.marginBoundary.topType != 0 && item.marginBoundary.endType != 0) {
                    builder.setTopRightCornerSize(originalShape.topRightCornerSize)
                } else builder.setTopRightCornerSize(0f)
                if (item.marginBoundary.bottomType != 0 && item.marginBoundary.startType != 0) {
                    builder.setBottomLeftCornerSize(originalShape.bottomLeftCornerSize)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.marginBoundary.bottomType != 0 && item.marginBoundary.endType != 0) {
                    builder.setBottomRightCornerSize(originalShape.bottomRightCornerSize)
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (item.marginBoundary.topType != 0 && item.marginBoundary.endType != 0) {
                    builder.setTopLeftCornerSize(originalShape.topLeftCornerSize)
                } else builder.setTopLeftCornerSize(0f)
                if (item.marginBoundary.topType != 0 && item.marginBoundary.startType != 0) {
                    builder.setTopRightCornerSize(originalShape.topRightCornerSize)
                } else builder.setTopRightCornerSize(0f)
                if (item.marginBoundary.bottomType != 0 && item.marginBoundary.endType != 0) {
                    builder.setBottomLeftCornerSize(originalShape.bottomLeftCornerSize)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.marginBoundary.bottomType != 0 && item.marginBoundary.startType != 0) {
                    builder.setBottomRightCornerSize(originalShape.bottomRightCornerSize)
                } else builder.setBottomRightCornerSize(0f)
            }
            it.shapeAppearanceModel = builder.build()
            it.updateLayoutParams<GridLayoutManager.LayoutParams> {
                topMargin = when (item.marginBoundary.topType) {
                    Boundary.LOCAL -> marginInfo.verticalLocal
                    else -> 0
                }
                bottomMargin = when (item.marginBoundary.bottomType) {
                    Boundary.LOCAL -> marginInfo.verticalLocal
                    else -> 0
                }
            }
        }
    }

    override fun onCreateGroupTitle(parent: ViewGroup) = MaterialTextView(parent.context).apply {
        id = R.id.formInternalContent
        setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium)
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle) {
        with(holder.getView<MaterialTextView>(R.id.formInternalContent)) {
            text = item.name
            updatePaddingRelative(
                paddingInfo.horizontalGlobal,
                paddingInfo.verticalGlobal,
                paddingInfo.horizontalGlobal,
                paddingInfo.verticalGlobal
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MaterialCardElevatedStyle) return false
        if (!super.equals(other)) return false

        if (elevation != other.elevation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (elevation?.hashCode() ?: 0)
        return result
    }
}