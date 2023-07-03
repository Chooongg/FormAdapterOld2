package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormSwitchProvider

class FormSwitch(name: CharSequence?, field: String?) : BaseForm(name, field) {
    override fun getItemProvider(adapter: FormAdapter) = FormSwitchProvider
}