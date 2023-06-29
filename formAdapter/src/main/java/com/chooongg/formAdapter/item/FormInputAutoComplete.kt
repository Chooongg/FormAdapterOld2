package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.BaseFormProvider

class FormInputAutoComplete(name: CharSequence?, field: String?) : BaseOptionForm(name, field) {
    override fun hasOpenOperation() = true

    override fun getItemProvider(adapter: FormAdapter): BaseFormProvider {
        TODO("Not yet implemented")
    }
}