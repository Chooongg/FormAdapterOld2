package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormHelper
import com.chooongg.formAdapter.provider.FormGroupTitleProvider

class FormGroupTitle internal constructor(
    name: CharSequence?
) : BaseForm(name, null) {

    override fun getItemProvider(helper: FormHelper) = FormGroupTitleProvider

}