package com.chooongg.formAdapter.item

import androidx.annotation.RestrictTo
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.FormCheckBoxProvider

fun FormCreator.addCheckBox(
    name: CharSequence?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = add(FormCheckBox(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addCheckBox(
    @StringRes nameRes: Int?, field: String? = null, block: (FormCheckBox.() -> Unit)? = null
) = add(FormCheckBox(nameRes, null, field).apply { block?.invoke(this) })

class FormCheckBox(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseMultipleOptionForm<BaseOption>(nameRes, name, field) {

    var selected: ArrayList<BaseOption>? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var content: Any? = null

    override fun hasOpenOperation() = false

    override fun getItemProvider(adapter: FormAdapter) = FormCheckBoxProvider

    fun checkedOption(option: BaseOption, isChecked: Boolean) {
        if (isChecked) {
            if (selected == null) selected = ArrayList()
            selected!!.add(option)
        } else {
            if (selected != null) {
                selected!!.remove(option)
                if (selected!!.isEmpty()) {
                    selected = null
                }
            }
        }
    }
}