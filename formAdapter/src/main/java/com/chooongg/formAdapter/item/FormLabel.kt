package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormLabelProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

fun FormCreator.addLabel(
    name: CharSequence?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = add(FormLabel(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addLabel(
    @StringRes nameRes: Int?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = add(FormLabel(nameRes, null, field).apply { block?.invoke(this) })

class FormLabel(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    var enableTopPadding: Boolean = true

    var enableBottomPadding: Boolean = true

    var color: FormColorStateListBlock? = null

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = FormLabelProvider
}
