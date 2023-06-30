package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.provider.FormLabelProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class FormLabel(name: CharSequence?, field: String?) : BaseForm(name, field) {

    var enableTopPadding: Boolean = true

    var enableBottomPadding: Boolean = true

    var color: FormColorStateListBlock? = null

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = FormLabelProvider
}
