package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormTextProvider

class FormText(
    name: CharSequence?, field: String?
) : BaseForm(name, field) {

    override fun getItemProvider(adapter: FormAdapter) = FormTextProvider

}