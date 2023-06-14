package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm

class GroupData {

    val items = mutableListOf<BaseForm>()

    fun add(item: BaseForm) = items.add(item)
}