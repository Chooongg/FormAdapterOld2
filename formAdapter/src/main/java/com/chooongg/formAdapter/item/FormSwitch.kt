package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormSwitchProvider

fun FormCreator.addSwitch(
    name: CharSequence?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = add(FormSwitch(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addSwitch(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = add(FormSwitch(nameRes, null, field).apply { block?.invoke(this) })

class FormSwitch(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    override fun getItemProvider(adapter: FormAdapter) = FormSwitchProvider
}