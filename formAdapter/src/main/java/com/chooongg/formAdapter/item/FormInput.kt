package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormInputProvider
import com.chooongg.formAdapter.provider.FormTextProvider

fun FormCreator.addInput(
    name: CharSequence?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = add(FormInput(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addInput(
    @StringRes nameRes: Int?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = add(FormInput(nameRes, null, field).apply { block?.invoke(this) })

class FormInput(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    var prefixText: CharSequence? = null

    var suffixText: CharSequence? = null

    var placeholderText: CharSequence? = null

    var showCounter: Boolean? = null

    var counterLength: Int = Int.MAX_VALUE

    var maxLines: Int = Int.MAX_VALUE

    var minLines: Int = 0

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormInputProvider else FormTextProvider
}