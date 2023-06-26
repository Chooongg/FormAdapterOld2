package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm

class SingleLineCreator : FormCreator {

    val _items = mutableListOf<BaseForm>()

    override fun getItems() = _items
}