package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormInputAutoCompleteProvider
import com.chooongg.formAdapter.provider.FormTextProvider

class FormInputAutoComplete(
    name: CharSequence?, field: String?
) : BaseOptionForm<String>(name, field) {

    var prefixText: CharSequence? = null

    var suffixText: CharSequence? = null

    var placeholderText: CharSequence? = null

    var isShowCounter: Boolean? = null

    var maxLength: Int = Int.MAX_VALUE

    var maxLines: Int = 1

    var minLines: Int = 0

    override fun hasOpenOperation() = false

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormInputAutoCompleteProvider else FormTextProvider
}