package com.chooongg.formAdapter.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle

/**
 * 表单分组样式
 */
abstract class Style {

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
}