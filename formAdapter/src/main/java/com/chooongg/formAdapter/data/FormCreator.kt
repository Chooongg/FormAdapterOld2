package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm

interface FormCreator {

    fun getItems(): MutableList<BaseForm>

    /**
     * 添加表单项
     */
    fun add(item:BaseForm) = getItems().add(item)
}