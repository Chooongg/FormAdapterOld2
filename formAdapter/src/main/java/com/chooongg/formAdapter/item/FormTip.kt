package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.provider.FormTipProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class FormTip(name: CharSequence?, field: String?) : BaseForm(name, field) {

    var enableTopPadding: Boolean = false

    var enableBottomPadding: Boolean = false

    var color: FormColorStateListBlock? = null

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = FormTipProvider
}