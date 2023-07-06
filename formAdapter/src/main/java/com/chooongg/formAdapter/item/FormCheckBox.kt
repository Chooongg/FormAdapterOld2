package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.BaseFormProvider

class FormCheckBox(name: CharSequence?, field: String?) : BaseMultipleOptionForm<BaseOption>(name, field) {

    override fun hasOpenOperation() = false

    override fun getItemProvider(adapter: FormAdapter): BaseFormProvider {
        TODO("Not yet implemented")
    }
}