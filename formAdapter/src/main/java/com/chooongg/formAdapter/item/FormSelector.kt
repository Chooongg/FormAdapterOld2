package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.FormSelectorProvider
import com.chooongg.formAdapter.provider.FormTextProvider

class FormSelector(name: CharSequence?, field: String?) : BaseOptionForm(name, field) {

    override fun hasOpenOperation() = true

    override fun getItemProvider(adapter: FormAdapter) =
        if (adapter.isEditable) FormSelectorProvider else FormTextProvider

    override fun getContentText(): CharSequence? {
        return (content as? BaseOption)?.getValue()?.toString() ?: content?.toString()
    }
}