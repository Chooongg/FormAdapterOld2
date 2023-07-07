package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormTipProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

fun FormCreator.addTip(
    name: CharSequence?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = add(FormTip(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addTip(
    @StringRes nameRes: Int?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = add(FormTip(nameRes, null, field).apply { block?.invoke(this) })

class FormTip(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    var enableTopPadding: Boolean = false

    var enableBottomPadding: Boolean = false

    var color: FormColorStateListBlock? = null

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = FormTipProvider
}