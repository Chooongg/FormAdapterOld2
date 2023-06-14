package com.chooongg.formAdapter.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.typeset.Typeset

/**
 * 表单分组样式
 */
abstract class Style(val defaultTypeset: Typeset) {

    open fun isNeedDecorationMargins() = true

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