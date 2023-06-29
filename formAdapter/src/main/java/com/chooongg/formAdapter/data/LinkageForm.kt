package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.item.BaseForm

class LinkageForm internal constructor(private val adapter: FormPartAdapter) {

    fun findItem(
        field: String, update: Boolean = true, isGlobal: Boolean = false, block: (BaseForm) -> Unit
    ) {
        if (isGlobal) {
            adapter.formAdapter.findOfField(field, update, block)
        } else {
            adapter.findOfField(field, update, block)
        }
    }
}