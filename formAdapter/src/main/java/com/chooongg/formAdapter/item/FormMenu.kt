package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.BaseFormProvider

class FormMenu(name: CharSequence?, field: String?) : BaseForm(name, field) {
    override fun getItemProvider(adapter: FormAdapter): BaseFormProvider {
        TODO("Not yet implemented")
    }
}