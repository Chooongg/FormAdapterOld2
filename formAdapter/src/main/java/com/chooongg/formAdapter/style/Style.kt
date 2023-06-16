package com.chooongg.formAdapter.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.boundary.FormMarginInfo
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.typeset.Typeset

/**
 * 表单分组样式
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

    /**
     * 创建样式布局
     */
    abstract fun onCreateStyleLayout(parent: ViewGroup): ViewGroup?

    /**
     * 绑定样式布局
     */
    abstract fun onBindStyleLayout(holder: FormViewHolder, item: BaseForm)

    /**
     * 创建分组标题
     */
    abstract fun onCreateGroupTitle(parent: ViewGroup): View

    /**
     * 绑定分组标题
     */
    abstract fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Style) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}