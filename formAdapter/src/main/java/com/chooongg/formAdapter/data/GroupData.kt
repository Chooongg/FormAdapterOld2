package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm

class GroupData {

    val items = mutableListOf<BaseForm>()

    /**
     * 添加表单项
     */
    fun add(item: BaseForm) = items.add(item)

    /**
     * 清空表单项
     */
    fun clearItems() = items.clear()
}