package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormInputAutoCompleteProvider
import com.chooongg.formAdapter.provider.FormTextProvider

fun FormCreator.addInputAutoComplete(
    name: CharSequence?, field: String? = null, block: (FormInputAutoComplete.() -> Unit)? = null
) = add(FormInputAutoComplete(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addInputAutoComplete(
    @StringRes nameRes: Int?,
    field: String? = null,
    block: (FormInputAutoComplete.() -> Unit)? = null
) = add(FormInputAutoComplete(nameRes, null, field).apply { block?.invoke(this) })

class FormInputAutoComplete(
    @StringRes nameRes: Int?, name: CharSequence?, field: String?
) : BaseOptionForm<String>(nameRes, name, field) {

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