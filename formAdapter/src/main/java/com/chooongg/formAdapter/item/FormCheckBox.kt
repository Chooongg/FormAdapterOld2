package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.BaseFormProvider

fun FormCreator.addCheckBox(
    name: CharSequence?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = add(FormCheckBox(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addCheckBox(
    @StringRes nameRes: Int?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = add(FormCheckBox(nameRes, null, field).apply { block?.invoke(this) })

class FormCheckBox(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseMultipleOptionForm<BaseOption>(nameRes, name, field) {

    override fun hasOpenOperation() = false

    override fun getItemProvider(adapter: FormAdapter): BaseFormProvider {
        TODO("Not yet implemented")
    }
}