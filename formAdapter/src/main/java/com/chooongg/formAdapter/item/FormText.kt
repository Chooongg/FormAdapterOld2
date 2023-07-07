package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormTextProvider

fun FormCreator.addText(
    name: CharSequence?, field: String? = null, block: (FormText.() -> Unit)? = null
) = add(FormText(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addText(
    @StringRes nameRes: Int?, field: String? = null, block: (FormText.() -> Unit)? = null
) = add(FormText(nameRes, null, field).apply { block?.invoke(this) })

class FormText(
    @StringRes nameRes: Int?, name: CharSequence?, field: String?
) : BaseForm(nameRes, name, field) {

    override fun getItemProvider(adapter: FormAdapter) = FormTextProvider

}