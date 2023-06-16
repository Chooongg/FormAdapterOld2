package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormHelper
import com.chooongg.formAdapter.provider.FormGroupTitleProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class FormGroupTitle internal constructor(
    name: CharSequence?
) : BaseForm(name, null) {

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(helper: FormHelper) = FormGroupTitleProvider

}