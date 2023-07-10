package com.chooongg.formAdapter.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.FormMarginInfo
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * 表单样式
 * 必须实现 equals 和 hashCode 方法
 */
abstract class Style(val defaultTypeset: Typeset) {

    private var _marginInfo: FormMarginInfo? = null

    private var _paddingInfo: FormPaddingInfo? = null

    protected val marginInfo get() = _marginInfo!!

    internal val paddingInfo get() = _paddingInfo!!

    internal fun createMarginAndPaddingInfo(context: Context) {
        _marginInfo = onCreateMarginInfo(context)
        _paddingInfo = onCreatePaddingInfo(context)
    }

    /**
     * 创建外边距信息
     */
    protected open fun onCreateMarginInfo(context: Context): FormMarginInfo {
        return FormMarginInfo(
            context.resources.getDimensionPixelSize(R.dimen.formVerticalLocalMarginSize),
            context.resources.getDimensionPixelSize(R.dimen.formVerticalGlobalMarginSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalLocalMarginSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize)
        )
    }

    /**
     * 创建内边距信息
     */
    protected open fun onCreatePaddingInfo(context: Context): FormPaddingInfo {
        return FormPaddingInfo(
            context.resources.getDimensionPixelSize(R.dimen.formVerticalLocalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formVerticalGlobalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalLocalPaddingSize),
            context.resources.getDimensionPixelSize(R.dimen.formHorizontalGlobalPaddingSize)
        )
    }

    abstract fun onBindStyle(partAdapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm)

    /**
     * 创建分组标题
     */
    abstract fun onCreateGroupTitle(parent: ViewGroup): View

    /**
     * 绑定分组标题
     */
    abstract fun onBindGroupTitle(holder: FormViewHolder, item: InternalFormGroupTitle)

    open fun getShapeAppearanceModel(partAdapter: FormPartAdapter): ShapeAppearanceModel {
        return partAdapter.formAdapter.shapeAppearanceModel ?: ShapeAppearanceModel()
    }

    open fun ShapeAppearanceModel.configShapeAppearanceModel(
        holder: FormViewHolder,
        item: BaseForm,
    ): ShapeAppearanceModel {
        val builder = toBuilder()
        if (holder.itemView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            if (item.marginBoundary.topType != 0 && item.marginBoundary.startType != 0) {
                builder.setTopLeftCornerSize(topLeftCornerSize)
            } else builder.setTopLeftCornerSize(0f)
            if (item.marginBoundary.topType != 0 && item.marginBoundary.endType != 0) {
                builder.setTopRightCornerSize(topRightCornerSize)
            } else builder.setTopRightCornerSize(0f)
            if (item.marginBoundary.bottomType != 0 && item.marginBoundary.startType != 0) {
                builder.setBottomLeftCornerSize(bottomLeftCornerSize)
            } else builder.setBottomLeftCornerSize(0f)
            if (item.marginBoundary.bottomType != 0 && item.marginBoundary.endType != 0) {
                builder.setBottomRightCornerSize(bottomRightCornerSize)
            } else builder.setBottomRightCornerSize(0f)
        } else {
            if (item.marginBoundary.topType != 0 && item.marginBoundary.endType != 0) {
                builder.setTopLeftCornerSize(topLeftCornerSize)
            } else builder.setTopLeftCornerSize(0f)
            if (item.marginBoundary.topType != 0 && item.marginBoundary.startType != 0) {
                builder.setTopRightCornerSize(topRightCornerSize)
            } else builder.setTopRightCornerSize(0f)
            if (item.marginBoundary.bottomType != 0 && item.marginBoundary.endType != 0) {
                builder.setBottomLeftCornerSize(bottomLeftCornerSize)
            } else builder.setBottomLeftCornerSize(0f)
            if (item.marginBoundary.bottomType != 0 && item.marginBoundary.startType != 0) {
                builder.setBottomRightCornerSize(bottomRightCornerSize)
            } else builder.setBottomRightCornerSize(0f)
        }
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Style) return false

        if (defaultTypeset != other.defaultTypeset) return false

        return true
    }

    override fun hashCode(): Int {
        return defaultTypeset.hashCode()
    }
}