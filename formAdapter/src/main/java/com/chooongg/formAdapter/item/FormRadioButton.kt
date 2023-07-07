package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.BaseFormProvider

fun FormCreator.addRadioButton(
    name: CharSequence?, field: String? = null, block: (FormRadioButton.() -> Unit)? = null
) = add(FormRadioButton(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addRadioButton(
    @StringRes nameRes: Int?, field: String? = null, block: (FormRadioButton.() -> Unit)? = null
) = add(FormRadioButton(nameRes, null, field).apply { block?.invoke(this) })

class FormRadioButton(
    @StringRes nameRes: Int?, name: CharSequence?, field: String?
) : BaseOptionForm<BaseOption>(nameRes, name, field) {

    override fun hasOpenOperation() = false

    override fun getItemProvider(adapter: FormAdapter): BaseFormProvider {
        TODO("Not yet implemented")
    }
}